package com.cj.smartworker.business.heart_rate.port.out

import com.cj.smartworker.domain.heart_rate.entity.HeartRate

fun interface SendHeartRateMessagePort {
    fun publish(heartRate: HeartRate)
}
