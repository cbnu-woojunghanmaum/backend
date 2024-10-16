package com.cj.smartworker.business.fcm.port.out

import com.cj.smartworker.business.fcm.dto.response.EmergencyReportResponse
import com.cj.smartworker.domain.fcm.valueobject.Emergency
import com.cj.smartworker.domain.member.entity.Member
import java.time.LocalDateTime

interface FindEmergencyReportPort {
    fun findReport(start: LocalDateTime, end: LocalDateTime): List<EmergencyReportResponse>
    fun findReport(
        start: LocalDateTime,
        end: LocalDateTime,
        emergency: Emergency,
    ): List<EmergencyReportResponse>

    fun findReport(member: Member, start: LocalDateTime, end: LocalDateTime): List<EmergencyReportResponse>
    fun findReport(member: Member): List<EmergencyReportResponse>

    fun findLatestReport(
        member: Member,
        emergency: Emergency,
        after: LocalDateTime,
        ): EmergencyReportResponse?
}
