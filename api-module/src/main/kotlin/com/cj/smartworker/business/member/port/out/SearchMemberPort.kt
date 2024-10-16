package com.cj.smartworker.business.member.port.out

import com.cj.smartworker.business.member.dto.response.MemberPagingResponse
import com.cj.smartworker.domain.member.entity.Member
import com.cj.smartworker.domain.member.valueobject.LoginId

interface SearchMemberPort {
    fun searchByLoginId(loginId: LoginId): MemberPagingResponse?
    fun searchByLoginIdReturnMember(loginId: LoginId): Member?
}
