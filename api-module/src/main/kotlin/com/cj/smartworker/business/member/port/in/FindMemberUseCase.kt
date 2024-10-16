package com.cj.smartworker.business.member.port.`in`

import com.cj.smartworker.business.member.dto.response.MemberResponse
import com.cj.smartworker.domain.member.valueobject.MemberId

fun interface FindMemberUseCase {
    fun findMember(memberId: MemberId): MemberResponse
}
