package com.cj.smartworker.business.heart_rate.port.out

import com.cj.smartworker.business.heart_rate.dto.response.HeartRateAggregateResponse
import com.cj.smartworker.domain.member.valueobject.MemberId
import java.time.LocalDateTime

interface AggregateHeartRatePort {
    fun aggregateHeartRate(
        memberId: MemberId,
        start: LocalDateTime,
        end: LocalDateTime,
    ): List<HeartRateAggregateResponse>
}
