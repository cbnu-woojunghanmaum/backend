package com.cj.smartworker.business.member.port.out

import com.cj.smartworker.business.member.dto.response.MemberPagingResponse
import com.cj.smartworker.domain.member.entity.Member


interface FindAdminPort {
    fun findAdmins(): List<Member>
    fun findAdminsPagingResponse(): List<MemberPagingResponse>
}
