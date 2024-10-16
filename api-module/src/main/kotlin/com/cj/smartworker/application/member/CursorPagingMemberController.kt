package com.cj.smartworker.application.member

import com.cj.smartworker.annotation.WebAdapter
import com.cj.smartworker.application.response.GenericResponse
import com.cj.smartworker.business.common.dto.request.CursorRequest
import com.cj.smartworker.business.common.dto.response.CursorResultResponse
import com.cj.smartworker.business.member.dto.response.MemberPagingResponse
import com.cj.smartworker.business.member.port.`in`.CursorPagingMemberUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "회원 API", description = "회원 가입, 로그인, 로그아웃, 회원 정보 수정, 회원 탈퇴 등 API 목록입니다.")
@WebAdapter
@RestController
@RequestMapping("/api/v1/member")
internal class CursorPagingMemberController(
    private val cursorPagingMemberUseCase: CursorPagingMemberUseCase
) {
    @Operation(
        summary = "직원 전체 커서 페이징 조회",
        description = "직원 전체 커서 페이징 조회를 진행합니다.\nAdmin만 가능합니다.",
        parameters = [
            Parameter(name = "next", description = "다음 페이지 번호", required = false),
            Parameter(name = "size", description = "페이지 사이즈", required = false),
        ]
    )
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/cursor-paging")
    fun pagingMember(
        @RequestParam("next") next: Int?,
        @RequestParam("size") size: Int?,
    ): GenericResponse<CursorResultResponse<MemberPagingResponse>> {
        val cursorRequest = next
            ?.let { CursorRequest(next, size) }
            ?: CursorRequest(size = size)

        cursorPagingMemberUseCase.findMembers(cursorRequest).let {
            return GenericResponse(
                data = it,
                statusCode = HttpStatus.OK.value(),
                success = true,
            )
        }
    }
}
