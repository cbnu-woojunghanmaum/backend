package com.cj.smartworker.business.member.port.`in`

import com.cj.smartworker.business.member.dto.response.MemberPagingResponse

fun interface FindAdminUseCase {
    fun findAdmins(): List<MemberPagingResponse>
}
