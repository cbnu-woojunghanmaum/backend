package com.cj.smartworker.business.heart_rate.service

import com.cj.smartworker.business.heart_rate.dto.response.HeartRateAggregateResponse
import com.cj.smartworker.business.heart_rate.port.`in`.AggregateHeartRateUseCase
import com.cj.smartworker.business.heart_rate.port.out.AggregateHeartRatePort
import com.cj.smartworker.domain.member.entity.Member
import com.cj.smartworker.domain.member.valueobject.MemberId
import com.cj.smartworker.domain.util.logger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
internal class AggregateHeartRateService(
    private val aggregateHeartRatePort: AggregateHeartRatePort,
): AggregateHeartRateUseCase {

    private val logger = logger()

    @Transactional(readOnly = true)
    override fun aggregateHeartRate(
        memberId: MemberId,
        start: LocalDateTime,
        end: LocalDateTime,
    ): List<HeartRateAggregateResponse> {
        val aggregateHeartRate = aggregateHeartRatePort.aggregateHeartRate(
            memberId = memberId,
            start = start,
            end = end,
        )

        logger.info("heart rate aggregate size: ${aggregateHeartRate.size}")
        return aggregateHeartRate
    }
}
