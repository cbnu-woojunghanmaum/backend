package com.cj.smartworker.domain.heart_rate.entity

import com.cj.smartworker.domain.common.RootAggregate
import com.cj.smartworker.domain.heart_rate.value.HeartRateValue
import com.cj.smartworker.domain.member.valueobject.MemberId
import java.time.LocalDateTime
import java.util.UUID

class HeartRate(
    private val _heartRateId: UUID,
    private val _memberId: MemberId,
    private val _value: HeartRateValue,
    private val _timestamp: LocalDateTime,
): RootAggregate<UUID>(_heartRateId) {
    val heartRateId: UUID
        get() = _heartRateId
    val memberId: MemberId
        get() = _memberId
    val value: HeartRateValue
        get() = _value
    val timestamp: LocalDateTime
        get() = _timestamp
}
