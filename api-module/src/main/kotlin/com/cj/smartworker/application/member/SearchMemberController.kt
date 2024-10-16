package com.cj.smartworker.application.member

import com.cj.smartworker.annotation.WebAdapter
import com.cj.smartworker.application.response.GenericResponse
import com.cj.smartworker.business.common.dto.response.CursorResultResponse
import com.cj.smartworker.business.member.dto.response.MemberPagingResponse
import com.cj.smartworker.business.member.port.`in`.SearchMemberUseCase
import com.cj.smartworker.domain.member.valueobject.LoginId
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "회원 API", description = "회원 가입, 로그인, 로그아웃, 회원 정보 수정, 회원 탈퇴 등 API 목록입니다.")
@WebAdapter
@RestController
@RequestMapping("/api/v1/member")
internal class SearchMemberController(
    private val searchMemberUseCase: SearchMemberUseCase,
) {
    @Operation(
        summary = "회원 로그인 아이디로 검색",
        description = "회원 로그인 아이디로 회원을 검색합니다. 유저가 없을 시 null을 반환합니다.",
    )
    @ApiResponse(responseCode = "200", description = "검색 성공")
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    fun searchByLoginId(
        @RequestParam("loginId") loginId: LoginId,
    ): GenericResponse<CursorResultResponse<MemberPagingResponse>> {
        val memberResponse = searchMemberUseCase.searchByLoginId(loginId)

        return GenericResponse(
            data = memberResponse,
            success = true,
        )
    }
}
