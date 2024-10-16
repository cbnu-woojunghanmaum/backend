package com.cj.smartworker.application.member

import com.cj.smartworker.annotation.WebAdapter
import com.cj.smartworker.application.response.GenericResponse
import com.cj.smartworker.business.member.port.`in`.CheckLoginIdUseCase
import com.cj.smartworker.domain.member.valueobject.LoginId
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "회원 API", description = "회원 가입, 로그인, 로그아웃, 회원 정보 수정, 회원 탈퇴 등 API 목록입니다.")
@WebAdapter
@RestController
@RequestMapping("/api/v1/member")
internal class CheckLoginIdController(
    private val checkLoginIdUseCase: CheckLoginIdUseCase,
) {
    @Operation(summary = "로그인 아이디 중복 체크", description = "로그인 아이디 중복 체크를 진행합니다.")
    @ApiResponse(responseCode = "200", description = "중복이면 false, 정상이면 true를 반환합니다.")
    @GetMapping("/check/login-id")
    fun checkLoginId(
        @RequestParam("loginId") loginId: LoginId,
    ): GenericResponse<Boolean> {
        val isOk = checkLoginIdUseCase.checkLoginId(loginId)
        return GenericResponse(
            data = isOk,
            statusCode = HttpStatus.OK.value(),
            success = true,
        )
    }
}
