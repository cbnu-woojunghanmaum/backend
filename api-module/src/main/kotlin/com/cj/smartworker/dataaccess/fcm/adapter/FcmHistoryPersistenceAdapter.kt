package com.cj.smartworker.dataaccess.fcm.adapter

import com.cj.smartworker.annotation.PersistenceAdapter
import com.cj.smartworker.business.fcm.dto.request.GPSRange
import com.cj.smartworker.business.fcm.dto.response.EmergencyReportResponse
import com.cj.smartworker.business.fcm.dto.response.HeartRateAggregateResponse
import com.cj.smartworker.business.fcm.port.out.AggregateHeartRateReportPort
import com.cj.smartworker.business.fcm.port.out.FindEmergencyReportPort
import com.cj.smartworker.business.fcm.port.out.SaveFcmHistoryPort
import com.cj.smartworker.dataaccess.fcm.entity.FcmHistoryJpaEntity
import com.cj.smartworker.dataaccess.fcm.entity.QFcmHistoryJpaEntity.fcmHistoryJpaEntity
import com.cj.smartworker.dataaccess.fcm.mapper.toEmergencyReportDto
import com.cj.smartworker.dataaccess.fcm.repository.FcmHistoryJpaRepository
import com.cj.smartworker.dataaccess.member.mapper.toJpaEntity
import com.cj.smartworker.domain.fcm.valueobject.Emergency
import com.cj.smartworker.domain.member.entity.Member
import com.querydsl.jpa.impl.JPAQueryFactory
import java.time.LocalDateTime

@PersistenceAdapter
internal class FcmHistoryPersistenceAdapter(
    private val fcmHistoryJpaRepository: FcmHistoryJpaRepository,
    private val queryFactory: JPAQueryFactory,
) : SaveFcmHistoryPort,
    FindEmergencyReportPort,
    AggregateHeartRateReportPort {

    override fun saveFcmHistory(
        reporter: Member,
        admins: Set<Member>,
        createdAt: LocalDateTime,
        x: Float,
        y: Float,
        emergency: Emergency,
    ) {
        val fcmHistoryJpaEntity = FcmHistoryJpaEntity(
            id = null,
            createdAt = createdAt,
            reporter = reporter.toJpaEntity(),
            admins = admins.map { it.toJpaEntity() }.toSet(),
            x = x,
            y = y,
            emergency = emergency,
            roundedXSmall = String.format("%.4f", x).toFloat(),
            roundedYSmall = String.format("%.4f", y).toFloat(),
            roundedXLarge = String.format("%.3f", x).toFloat(),
            roundedYLarge = String.format("%.3f", y).toFloat(),
        )
        fcmHistoryJpaRepository.save(fcmHistoryJpaEntity)
    }

    override fun findReport(start: LocalDateTime, end: LocalDateTime): List<EmergencyReportResponse> {
        return fcmHistoryJpaRepository.findByCreatedAtBetweenOrderByCreatedAtDesc(
            start = start,
            end = end,
        ).map {
            it.toEmergencyReportDto()
        }
    }

    override fun findReport(
        start: LocalDateTime,
        end: LocalDateTime,
        emergency: Emergency,
    ): List<EmergencyReportResponse> {
        return queryFactory.select(fcmHistoryJpaEntity)
            .from(fcmHistoryJpaEntity)
            .where(
                fcmHistoryJpaEntity.createdAt.goe(start),
                fcmHistoryJpaEntity.createdAt.loe(end),
                fcmHistoryJpaEntity.emergency.eq(emergency),
            )
            .orderBy(fcmHistoryJpaEntity.createdAt.desc())
            .fetch()
            .map { it.toEmergencyReportDto() }
    }

    override fun findReport(member: Member, start: LocalDateTime, end: LocalDateTime): List<EmergencyReportResponse> {
        return fcmHistoryJpaRepository.findByReporterAndCreatedAtBetweenOrderByCreatedAtDesc(
            memberJapEntity = member.toJpaEntity(),
            start = start,
            end = end,
        ).map {
            it.toEmergencyReportDto()
        }
    }

    override fun findReport(member: Member): List<EmergencyReportResponse> {
        return fcmHistoryJpaRepository.findByReporterOrderByCreatedAtDesc(member.toJpaEntity()).map {
            it.toEmergencyReportDto()
        }
    }

    override fun findLatestReport(
        member: Member,
        emergency: Emergency,
        after: LocalDateTime,
    ): EmergencyReportResponse? {
        return queryFactory.select(fcmHistoryJpaEntity)
            .from(fcmHistoryJpaEntity)
            .where(
                fcmHistoryJpaEntity.reporter.eq(member.toJpaEntity()),
                fcmHistoryJpaEntity.emergency.eq(emergency),
                fcmHistoryJpaEntity.createdAt.after(after),
            )
            .orderBy(fcmHistoryJpaEntity.createdAt.desc())
            .limit(1)
            .fetchOne()
            ?.toEmergencyReportDto()
    }

    override fun aggregate(
        start: LocalDateTime,
        end: LocalDateTime,
        gpsRange: GPSRange,
        emergency: Emergency,
    ): List<HeartRateAggregateResponse> {
        return if (gpsRange == GPSRange.LARGE) {
            fcmHistoryJpaRepository.aggregateMapLarge(
                start = start,
                end = end,
                emergency = emergency.name,
            ).map {
                HeartRateAggregateResponse(
                    x = it.getX(),
                    y = it.getY(),
                    count = it.getCount(),
                )
            }
        } else {
            fcmHistoryJpaRepository.aggregateMapSmall(
                start = start,
                end = end,
                emergency = emergency.name,
            ).map {
                HeartRateAggregateResponse(
                    x = it.getX(),
                    y = it.getY(),
                    count = it.getCount(),
                )
            }
        }
    }


//        val roundX =
//            if (gpsRange == GPSRange.LARGE) fcmHistoryJpaEntity.roundedXLarge else fcmHistoryJpaEntity.roundedXSmall
//        val roundY =
//            if (gpsRange == GPSRange.LARGE) fcmHistoryJpaEntity.roundedYLarge else fcmHistoryJpaEntity.roundedYSmall
//        return queryFactory.select(
//            Projections.constructor(
//                HeartRateAggregateResponse::class.java,
//                round(fcmHistoryJpaEntity.x.avg(), 6),
//                round(fcmHistoryJpaEntity.y.avg(), 6),
//                count,
//            )
//        ).from(fcmHistoryJpaEntity)
//            .where(
//                fcmHistoryJpaEntity.emergency.eq(Emergency.HEART_RATE),
//                fcmHistoryJpaEntity.createdAt.between(start, end),
//            ).groupBy(roundX, roundY)
//            .orderBy(count.desc())
//            .limit(1000)
//            .fetch()

}
