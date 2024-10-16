package com.cj.smartworker.mongo.step.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.FieldType
import java.time.LocalDateTime

@Document(collection = "step_count")
class StepCountDocument(
    @Id
    @Field(name = "_id", targetType = FieldType.STRING)
    var id: String? = null,

    @Field(name = "timestamp")
    val timestamp: LocalDateTime,

    @Field(name = "step_count")
    val stepCount: Int,

    @Field(name = "member_id")
    val memberId: Long,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is StepCountDocument) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
