package com.cj.smartworker.business.member.port.`in`

import com.cj.smartworker.business.common.dto.response.CursorResultResponse
import com.cj.smartworker.business.member.dto.response.MemberPagingResponse
import com.cj.smartworker.domain.member.valueobject.LoginId

interface SearchMemberUseCase {

    /**
     * 이름으로 회원 검색
     * name과 일치하거나 해당 name으로 시작하는 회원을 검색한다.
     * Admin 권한을 가진 사람만 사용 가능하다.
     */
    fun searchByLoginId(loginId1: LoginId): CursorResultResponse<MemberPagingResponse>
}
