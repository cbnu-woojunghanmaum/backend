package com.cj.smartworker.mongo.heart_rate.value

import java.time.LocalDateTime

data class HeartRateDataId(
    val memberId: Long,
    val timestamp: LocalDateTime,
)
