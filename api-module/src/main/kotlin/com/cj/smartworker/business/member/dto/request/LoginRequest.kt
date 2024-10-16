package com.cj.smartworker.business.member.dto.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "로그인 요청값")
data class LoginRequest(
    @Schema(description = "로그인 아이디")
    val loginId: String,
    @Schema(description = "로그인 비밀번호")
    val password: String,
    @Schema(description = "디바이스 토큰", nullable = true)
    val token: String?,
)
