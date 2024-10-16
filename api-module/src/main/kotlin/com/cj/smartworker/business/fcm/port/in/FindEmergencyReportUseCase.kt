package com.cj.smartworker.business.fcm.port.`in`

import com.cj.smartworker.business.fcm.dto.response.EmergencyReportResponse
import com.cj.smartworker.domain.fcm.valueobject.Emergency
import com.cj.smartworker.domain.member.entity.Member
import com.cj.smartworker.domain.member.valueobject.LoginId
import com.cj.smartworker.domain.member.valueobject.MemberId
import java.time.LocalDateTime

interface FindEmergencyReportUseCase {
    fun findReport(start: LocalDateTime, end: LocalDateTime): List<EmergencyReportResponse>
    fun findReport(start: LocalDateTime, end: LocalDateTime, emergency: Emergency): List<EmergencyReportResponse>

    fun findReport(member: Member, start: LocalDateTime, end: LocalDateTime): List<EmergencyReportResponse>

    fun findReport(memberId: MemberId, start: LocalDateTime, end: LocalDateTime): List<EmergencyReportResponse>

    fun findReport(loginId1: LoginId): List<EmergencyReportResponse>
    fun findReport(loginId1: LoginId, start: LocalDateTime, end: LocalDateTime): List<EmergencyReportResponse>
}
