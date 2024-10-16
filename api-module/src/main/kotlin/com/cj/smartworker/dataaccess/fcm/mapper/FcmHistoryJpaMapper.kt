package com.cj.smartworker.dataaccess.fcm.mapper

import com.cj.smartworker.business.fcm.dto.response.EmergencyReportResponse
import com.cj.smartworker.dataaccess.fcm.entity.FcmHistoryJpaEntity

fun FcmHistoryJpaEntity.toEmergencyReportDto(): EmergencyReportResponse = let {
    EmergencyReportResponse(
        id = it.id!!,
        createdAt = it.createdAt,
        reporter = it.reporter.employeeName,
        x = it.x,
        y = it.y,
        emergency = it.emergency,
        loginId = it.reporter.loginId,
        phone = it.reporter.phone,
        reporterId = it.reporter.id!!,
    )
}
