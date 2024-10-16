package com.cj.smartworker.mongo.heart_rate.repository

import com.cj.smartworker.mongo.heart_rate.entity.HeartRateDocument
import com.cj.smartworker.mongo.heart_rate.repository.Query.ADD_FIELDS_DATE_OPERATION
import com.cj.smartworker.mongo.heart_rate.repository.Query.ADD_FIELDS_OPERATION
import com.cj.smartworker.mongo.heart_rate.repository.Query.GROUP_OPERATION
import com.cj.smartworker.mongo.heart_rate.repository.Query.MATCH_OPERATION
import com.cj.smartworker.mongo.heart_rate.repository.Query.SORT_OPERATION
import com.cj.smartworker.mongo.heart_rate.value.HeartRateDataId
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.MongoRepository
import java.io.Serializable
import java.time.LocalDateTime

interface HeartRateMongoRepository: MongoRepository<HeartRateDocument, HeartRateDataId> {
    @Aggregation(pipeline = [
        MATCH_OPERATION,
        ADD_FIELDS_OPERATION,
        GROUP_OPERATION,
        ADD_FIELDS_DATE_OPERATION,
        SORT_OPERATION,
    ])
    fun aggregateHeartRate(memberId: Long, start: LocalDateTime, end: LocalDateTime): List<HeartRateAggregateResponseDto>
}

data class HeartRateAggregateResponseDto(
    val averageHeartRate: Float,
    val maxHeartRate: Int,
    val minHeartRate: Int,
    val dateTime: LocalDateTime,
): Serializable
