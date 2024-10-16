package com.cj.smartworker.business.fcm.service

import com.cj.smartworker.business.fcm.dto.request.GPSRange
import com.cj.smartworker.business.fcm.dto.response.HeartRateAggregateResponse
import com.cj.smartworker.business.fcm.port.`in`.AggregateHeartRateReportUseCase
import com.cj.smartworker.business.fcm.port.out.AggregateHeartRateReportPort
import com.cj.smartworker.domain.fcm.valueobject.Emergency
import com.cj.smartworker.domain.util.logger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StopWatch
import java.time.LocalDateTime

@Service
internal class AggregateHeartRateReportService(
    private val aggregateHeartRateReportPort: AggregateHeartRateReportPort,
) : AggregateHeartRateReportUseCase {
    private val logger = logger()

    @Transactional(readOnly = true)
    override fun aggregate(
        start: LocalDateTime,
        end: LocalDateTime,
        gpsRange: GPSRange,
        emergency: Emergency,
    ): List<HeartRateAggregateResponse> {
        val stopWatch = StopWatch()
        stopWatch.start()

        val aggregate = aggregateHeartRateReportPort.aggregate(
            start = start,
            end = end,
            gpsRange = gpsRange,
            emergency = emergency,
        )
        stopWatch.stop()
        logger.info("심박수 신고 집계 Service Layer 시간: ${stopWatch.totalTimeMillis} ms")

        return aggregate
    }
}
