package com.cj.smartworker.business.fcm.dto.command

data class FcmSendCommand(
    val token: String,
    val title: String,
    val body: String,
)
