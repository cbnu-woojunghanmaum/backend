package com.cj.smartworker.business.fcm.service

import com.cj.smartworker.business.fcm.dto.response.EmergencyReportResponse
import com.cj.smartworker.business.fcm.port.`in`.FindEmergencyReportUseCase
import com.cj.smartworker.business.fcm.port.out.FindEmergencyReportPort
import com.cj.smartworker.business.member.port.out.FindMemberPort
import com.cj.smartworker.business.member.port.out.SearchMemberPort
import com.cj.smartworker.business.member.util.MaskingUtil
import com.cj.smartworker.domain.fcm.valueobject.Emergency
import com.cj.smartworker.domain.member.entity.Member
import com.cj.smartworker.domain.member.valueobject.EmployeeName
import com.cj.smartworker.domain.member.valueobject.LoginId
import com.cj.smartworker.domain.member.valueobject.MemberId
import com.cj.smartworker.domain.member.valueobject.Phone
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
internal class FindEmergencyReportService(
    private val findEmergencyReportPort: FindEmergencyReportPort,
    private val findMemberPort: FindMemberPort,
    private val searchMemberPort: SearchMemberPort
): FindEmergencyReportUseCase {

    override fun findReport(start: LocalDateTime, end: LocalDateTime): List<EmergencyReportResponse> {
        return findEmergencyReportPort.findReport(
            start = start,
            end = end,
        ).toMask()
    }

    override fun findReport(
        start: LocalDateTime,
        end: LocalDateTime,
        emergency: Emergency
    ): List<EmergencyReportResponse> {
        return findEmergencyReportPort.findReport(
            start = start,
            end = end,
            emergency = emergency,
        ).toMask()
    }

    override fun findReport(member: Member, start: LocalDateTime, end: LocalDateTime): List<EmergencyReportResponse> {
        return findEmergencyReportPort.findReport(
            member = member,
            start = start,
            end = end,
        )
    }

    /**
     * Admin이 특정 회원의 신고 내역 조회
     */
    override fun findReport(memberId: MemberId, start: LocalDateTime, end: LocalDateTime): List<EmergencyReportResponse> {
        val member: Member = findMemberPort.findById(memberId) ?: run {
            throw IllegalArgumentException("해당 회원을 찾지 못했습니다.")
        }
        return findEmergencyReportPort.findReport(
            member = member,
            start = start,
            end = end,
        )
    }

    override fun findReport(loginId1: LoginId): List<EmergencyReportResponse> {
        val member = searchMemberPort.searchByLoginIdReturnMember(loginId1) ?: run {
            return listOf()
        }
        return findEmergencyReportPort.findReport(member).toMask()
    }

    override fun findReport(
        loginId1: LoginId,
        start: LocalDateTime,
        end: LocalDateTime
    ): List<EmergencyReportResponse> {
        val member = searchMemberPort.searchByLoginIdReturnMember(loginId1) ?: run {
            return listOf()
        }
        return findEmergencyReportPort.findReport(
            member = member,
            start = start,
            end = end,
        ).toMask()
    }
}

fun List<EmergencyReportResponse>.toMask(): List<EmergencyReportResponse> = let { reportList ->
    reportList.map { report ->
        report.apply {
            reporter = MaskingUtil.maskEmployeeName(EmployeeName(reporter))
            phone = MaskingUtil.maskPhone(Phone(phone))
            loginId = MaskingUtil.maskLoginId(LoginId(loginId))
        }
    }
}
