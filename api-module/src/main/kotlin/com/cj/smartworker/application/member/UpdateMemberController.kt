package com.cj.smartworker.application.member

import com.cj.smartworker.annotation.WebAdapter
import com.cj.smartworker.application.response.GenericResponse
import com.cj.smartworker.business.member.dto.command.UpdateMemberCommand
import com.cj.smartworker.business.member.port.`in`.UpdateMemberUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "회원 API", description = "회원 가입, 로그인, 로그아웃, 회원 정보 수정, 회원 탈퇴 등 API 목록입니다.")
@WebAdapter
@RestController
@RequestMapping("/api/v1/member")
internal class UpdateMemberController(
    private val updateMemberUseCase: UpdateMemberUseCase,
) {

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "회원 정보 수정",
        description = "회원 정보를 수정합니다. 관리자 권한이 필요합니다.",
    )
    @ApiResponse(responseCode = "200", description = "회원 정보 수정 성공")
    @PutMapping("/manage")
    fun changeMemberInfo(
        @RequestBody command: UpdateMemberCommand,
    ): GenericResponse<Unit> {
        updateMemberUseCase.update(command)

        return GenericResponse(
            data = Unit,
            statusCode = HttpStatus.OK.value(),
            success = true,
        )
    }
}
