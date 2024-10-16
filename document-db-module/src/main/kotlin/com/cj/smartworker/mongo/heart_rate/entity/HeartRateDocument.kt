package com.cj.smartworker.mongo.heart_rate.entity

import com.cj.smartworker.mongo.heart_rate.value.HeartRateDataId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = "heart_rate")
class HeartRateDocument(
    @Id
    val id: HeartRateDataId,

    @Field(name = "heartRate")
    val heartRate: Double,

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is HeartRateDocument) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
