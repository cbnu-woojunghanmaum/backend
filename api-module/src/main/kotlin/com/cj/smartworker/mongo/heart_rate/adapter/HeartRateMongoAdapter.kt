package com.cj.smartworker.mongo.heart_rate.adapter

import com.cj.smartworker.annotation.MongoAdapter
import com.cj.smartworker.business.heart_rate.dto.response.HeartRateAggregateResponse
import com.cj.smartworker.business.heart_rate.port.out.AggregateHeartRatePort
import com.cj.smartworker.domain.member.valueobject.MemberId
import com.cj.smartworker.mongo.heart_rate.repository.HeartRateAggregateResponseDto
import com.cj.smartworker.mongo.heart_rate.repository.HeartRateMongoRepository
import java.time.LocalDateTime

@MongoAdapter
internal class HeartRateMongoAdapter(
    private val heartRateMongoRepository: HeartRateMongoRepository,
) : AggregateHeartRatePort {

    override fun aggregateHeartRate(
        memberId: MemberId,
        start: LocalDateTime,
        end: LocalDateTime,
    ): List<HeartRateAggregateResponse> {
        val aggregateHeartRate: List<HeartRateAggregateResponseDto> = heartRateMongoRepository.aggregateHeartRate(
            memberId = memberId.id,
            start = start,
            end = end,
        )
        return aggregateHeartRate.map {
            HeartRateAggregateResponse(
                averageHeartRate = it.averageHeartRate,
                maxHeartRate = it.maxHeartRate,
                minHeartRate = it.minHeartRate,
                dateTime = it.dateTime,
            )
        }
    }
}
