package com.cj.smartworker.kafka.model

import java.io.Serializable

data class HeartRateDto(
    val memberId: Long,
    val heartRate: Int,
    val timestamp: Long,
) : Serializable
