package com.cj.smartworker.application.member

import com.cj.smartworker.annotation.WebAdapter
import com.cj.smartworker.application.response.GenericResponse
import com.cj.smartworker.business.member.port.`in`.DeleteMemberUseCase
import com.cj.smartworker.domain.member.valueobject.MemberId
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Tag(name = "회원 API", description = "회원 가입, 로그인, 로그아웃, 회원 정보 수정, 회원 탈퇴 등 API 목록입니다.")
@WebAdapter
@RestController
@RequestMapping("/api/v1/member")
internal class DeleteMemberController(
    private val deleteMemberUseCase: DeleteMemberUseCase,
) {

    @Operation(
        summary = "회원 id로 회원 삭제",
        description = "회원 id로 회원을 삭제합니다. Admin권한에서 사용 가능합니다.",
    )
    @ApiResponse(responseCode = "204", description = "삭제 성공")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/member/{memberId}")
    fun deleteMember(@PathVariable memberId: Long): GenericResponse<Unit> {
        deleteMemberUseCase.deleteByMemberId(MemberId(memberId))

        return GenericResponse(
            data = Unit,
            statusCode = HttpStatus.NO_CONTENT.value(),
            success = true,
        )
    }
}
