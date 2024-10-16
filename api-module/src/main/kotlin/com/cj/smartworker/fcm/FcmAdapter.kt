package com.cj.smartworker.fcm

import com.cj.smartworker.annotation.ExternalAdapter
import com.cj.smartworker.business.fcm.port.out.FcmPushPort
import com.cj.smartworker.domain.member.valueobject.EmployeeName
import com.cj.smartworker.domain.member.valueobject.Phone
import com.cj.smartworker.domain.util.logger
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@ExternalAdapter
internal class FcmAdapter : FcmPushPort {
    private val logger = logger()

    /**
     * if return is true then message is sent successfully
     */
    override fun sendMessage(
        targetToken: String,
        title: String,
        body: String,
        x: Float,
        y: Float,
        age: Int,
        employeeName: EmployeeName,
        phone: Phone,
        createdAt: LocalDateTime,
    ): Boolean {
        val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        try {
            val messagingSender: FirebaseMessaging = FirebaseMessaging.getInstance()
            val message = Message.builder()
                .setToken(targetToken)
                .putAllData(
                    mapOf(
                        "x" to x.toString(),
                        "y" to y.toString(),
                        "age" to age.toString(),
                        "employeeName" to employeeName.employeeName,
                        "phone" to phone.phone,
                        "createdAt" to createdAt.format(pattern),
                    )
                )
                .setNotification(
                    Notification.builder().setTitle(title).setBody(body)
                        .build()
                ).build()
            val sendingPath = messagingSender.send(message)
            return sendingPath != null
        } catch (e: Exception) {
            logger.error("Failed to send message to $targetToken", e)
            return false
        }
    }
}
