package com.cj.smartworker.dataaccess.fcm.repository

import com.cj.smartworker.dataaccess.fcm.dto.HeartRateAggregateVo
import com.cj.smartworker.dataaccess.fcm.entity.FcmHistoryJpaEntity
import com.cj.smartworker.dataaccess.member.entity.MemberJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface FcmHistoryJpaRepository: JpaRepository<FcmHistoryJpaEntity, Long> {

    // start end 사이의 신고 이력
    fun findByCreatedAtBetweenOrderByCreatedAtDesc(start: LocalDateTime, end: LocalDateTime): List<FcmHistoryJpaEntity>

    fun findByReporterAndCreatedAtBetweenOrderByCreatedAtDesc(
        memberJapEntity: MemberJpaEntity,
        start: LocalDateTime,
        end: LocalDateTime
    ): List<FcmHistoryJpaEntity>

    fun findByReporterOrderByCreatedAtDesc(memberJpaEntity: MemberJpaEntity): List<FcmHistoryJpaEntity>

    @Query(
        value = """
        SELECT ROUND(AVG(x), 6) as x, ROUND(AVG(y), 6) as y, COUNT(*) as count
        FROM fcm_history
        WHERE created_at BETWEEN :start AND :end 
        AND emergency = :emergency
        GROUP BY rounded_x_small, rounded_y_small
        ORDER BY count DESC
        LIMIT 1000;
        """,
        nativeQuery = true,
    )
    fun aggregateMapSmall(
        @Param("start") start: LocalDateTime,
        @Param("end") end: LocalDateTime,
        @Param("emergency") emergency: String,
    ): List<HeartRateAggregateVo>

    @Query(
        value = """
        SELECT ROUND(AVG(x), 6) as x, ROUND(AVG(y), 6) as y, COUNT(*) as count
        FROM fcm_history
        WHERE created_at BETWEEN :start AND :end
        AND emergency = :emergency
        GROUP BY rounded_x_large, rounded_y_large
        ORDER BY count DESC
        LIMIT 1000;
        """,
        nativeQuery = true,
        name = "aggregateMapLarge"
    )
    fun aggregateMapLarge(
        @Param("start") start: LocalDateTime,
        @Param("end") end: LocalDateTime,
        @Param("emergency") emergency: String,
    ): List<HeartRateAggregateVo>
}
