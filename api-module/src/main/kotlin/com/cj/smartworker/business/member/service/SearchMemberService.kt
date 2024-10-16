package com.cj.smartworker.business.member.service

import com.cj.smartworker.business.common.dto.response.CursorResultResponse
import com.cj.smartworker.business.member.dto.response.MemberPagingResponse
import com.cj.smartworker.business.member.port.`in`.SearchMemberUseCase
import com.cj.smartworker.business.member.port.out.SearchMemberPort
import com.cj.smartworker.business.member.util.MaskingUtil
import com.cj.smartworker.domain.member.valueobject.EmployeeName
import com.cj.smartworker.domain.member.valueobject.LoginId
import com.cj.smartworker.domain.member.valueobject.Phone
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class SearchMemberService(
    private val searchMemberPort: SearchMemberPort,
): SearchMemberUseCase {
    @Transactional(readOnly = true)
    override fun searchByLoginId(loginId1: LoginId): CursorResultResponse<MemberPagingResponse> {
        val memberPagingResponse = searchMemberPort.searchByLoginId(loginId1) ?: run {
            return CursorResultResponse(listOf(), false, null)
        }
        memberPagingResponse.apply {
            phone = MaskingUtil.maskPhone(Phone(phone))
            employeeName = MaskingUtil.maskEmployeeName(EmployeeName(employeeName))
            loginId = MaskingUtil.maskLoginId(LoginId(loginId))
        }
        return CursorResultResponse(listOf(memberPagingResponse), false, memberPagingResponse.memberId)
    }
}
