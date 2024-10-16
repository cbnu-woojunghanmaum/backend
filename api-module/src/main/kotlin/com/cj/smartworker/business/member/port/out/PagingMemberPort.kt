package com.cj.smartworker.business.member.port.out

import com.cj.smartworker.business.common.dto.request.CursorRequest
import com.cj.smartworker.business.member.dto.response.MemberPagingResponse

fun interface PagingMemberPort {
    fun pagingMembers(cursorRequest: CursorRequest): List<MemberPagingResponse>
}
