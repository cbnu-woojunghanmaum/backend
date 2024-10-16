package com.cj.smartworker.application.member

import com.cj.smartworker.annotation.WebAdapter
import com.cj.smartworker.application.response.GenericResponse
import com.cj.smartworker.business.member.dto.command.SignupCommand
import com.cj.smartworker.business.member.port.`in`.SignupUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "회원 API", description = "회원 가입, 로그인, 로그아웃, 회원 정보 수정, 회원 탈퇴 등 API 목록입니다.")
@WebAdapter
@RestController
@RequestMapping("/api/v1/member")
internal class SignupController(
    private val signupUseCase: SignupUseCase,
) {
    @Operation(summary = "회원 가입", description = "회원 가입을 진행합니다.")
    @ApiResponse(responseCode = "201", description = "회원 가입 성공")
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/signup")
    fun signup(
        @RequestBody command: SignupCommand,
    ): GenericResponse<Unit> {
        signupUseCase.signup(command)

        return GenericResponse(
            data = Unit,
            statusCode = HttpStatus.CREATED.value(),
            success = true,
        )
    }
}
