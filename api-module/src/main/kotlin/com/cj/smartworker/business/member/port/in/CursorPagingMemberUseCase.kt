package com.cj.smartworker.business.member.port.`in`

import com.cj.smartworker.business.common.dto.request.CursorRequest
import com.cj.smartworker.business.common.dto.response.CursorResultResponse
import com.cj.smartworker.business.member.dto.response.MemberPagingResponse

fun interface CursorPagingMemberUseCase {
    fun findMembers(cursorRequest: CursorRequest): CursorResultResponse<MemberPagingResponse>
}
