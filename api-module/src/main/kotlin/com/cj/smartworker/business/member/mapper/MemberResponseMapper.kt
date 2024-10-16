package com.cj.smartworker.business.member.mapper

import com.cj.smartworker.business.member.dto.response.MemberResponse
import com.cj.smartworker.domain.member.entity.Member
import org.springframework.stereotype.Component

@Component
class MemberResponseMapper {
    fun memberToMemberResponse(member: Member): MemberResponse {
        if (member.memberId == null) {
            throw IllegalArgumentException("MemberId가 없습니다.")
        }
        return MemberResponse(
            memberId = member.memberId!!.id,
            loginId = member.loginId.loginId,
            email = member.email?.email,
            phone = member.phone.phone,
            authorities = member.authorities.map { it.authority }.toSet(),
            createdAt = member.createdAt,
            employeeName = member.employeeName.employeeName,
            gender = member.gender,
            year = member.year.year,
            month = member.month.month,
            day = member.day.day,
            heartRateThreshold = member.heartRateThreshold.value,
        )
    }
}
