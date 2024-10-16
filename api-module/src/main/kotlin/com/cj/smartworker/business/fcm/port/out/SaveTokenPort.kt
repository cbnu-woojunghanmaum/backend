package com.cj.smartworker.business.fcm.port.out

import com.cj.smartworker.domain.fcm.entity.DeviceToken

fun interface SaveTokenPort {
    fun saveToken(deviceToken: DeviceToken): DeviceToken
}
