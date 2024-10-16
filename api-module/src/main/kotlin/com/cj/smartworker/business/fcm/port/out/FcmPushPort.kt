package com.cj.smartworker.business.fcm.port.out

import com.cj.smartworker.domain.member.valueobject.EmployeeName
import com.cj.smartworker.domain.member.valueobject.Phone
import java.time.LocalDateTime

fun interface FcmPushPort {
    fun sendMessage(
        targetToken: String,
        title: String,
        body: String,
        x: Float,
        y: Float,
        age: Int,
        employeeName: EmployeeName,
        phone: Phone,
        createdAt: LocalDateTime,
    ): Boolean
}
