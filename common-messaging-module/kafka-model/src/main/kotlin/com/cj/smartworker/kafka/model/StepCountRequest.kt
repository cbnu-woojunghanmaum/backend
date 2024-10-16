package com.cj.smartworker.kafka.model

import java.io.Serializable

data class StepCountRequest(
    val memberId: Long,
    val step: Int,
    val timestamp: Long,
): Serializable
