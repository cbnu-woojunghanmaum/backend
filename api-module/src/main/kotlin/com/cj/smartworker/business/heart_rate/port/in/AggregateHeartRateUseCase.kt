package com.cj.smartworker.business.heart_rate.port.`in`

import com.cj.smartworker.business.heart_rate.dto.response.HeartRateAggregateResponse
import com.cj.smartworker.domain.member.valueobject.MemberId
import java.time.LocalDateTime

interface AggregateHeartRateUseCase {
    fun aggregateHeartRate(
        memberId: MemberId,
        start: LocalDateTime,
        end: LocalDateTime,
    ): List<HeartRateAggregateResponse>
}
