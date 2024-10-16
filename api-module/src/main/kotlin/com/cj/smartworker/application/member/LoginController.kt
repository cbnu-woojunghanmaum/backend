package com.cj.smartworker.application.member

import com.cj.smartworker.annotation.WebAdapter
import com.cj.smartworker.application.response.GenericResponse
import com.cj.smartworker.business.member.dto.TokenResponse
import com.cj.smartworker.business.member.dto.request.LoginRequest
import com.cj.smartworker.business.member.port.`in`.LoginUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "회원 API", description = "회원 가입, 로그인, 로그아웃, 회원 정보 수정, 회원 탈퇴 등 API 목록입니다.")
@WebAdapter
@RestController
@RequestMapping("/api/v1/member")
internal class LoginController(
    private val loginUseCase: LoginUseCase,
) {

    @Operation(summary = "로그인", description = "로그인을 진행합니다. token은 nullable합니다.")
    @ApiResponse(responseCode = "200", description = "로그인 성공")
    @PostMapping("/login")
    fun login(
        @RequestBody loginRequest: LoginRequest,
    ): GenericResponse<TokenResponse> {
        val token = loginUseCase.login(loginRequest)

        return GenericResponse(
            data = token,
            statusCode = HttpStatus.OK.value(),
            success = true,
        )
    }
}
