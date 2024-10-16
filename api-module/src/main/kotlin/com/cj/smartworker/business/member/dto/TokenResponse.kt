package com.cj.smartworker.business.member.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "토큰 발급 응답")
data class TokenResponse(

    @Schema(description = "JWT 토큰")
    val token: String,
    @Schema(description = "리프레시 토큰")
    val refreshToken: String,
)
