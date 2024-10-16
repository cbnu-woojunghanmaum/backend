package com.cj.smartworker.business.fcm.port.`in`

import com.cj.smartworker.business.fcm.dto.request.GPSRange
import com.cj.smartworker.business.fcm.dto.response.HeartRateAggregateResponse
import com.cj.smartworker.domain.fcm.valueobject.Emergency
import java.time.LocalDateTime

interface AggregateHeartRateReportUseCase {
    fun aggregate(
        start: LocalDateTime,
        end: LocalDateTime,
        gpsRange: GPSRange,
        emergency: Emergency,
    ): List<HeartRateAggregateResponse>
}