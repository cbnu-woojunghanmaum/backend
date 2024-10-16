package com.cj.smartworker.business.member.port.`in`

import com.cj.smartworker.business.member.dto.TokenResponse
import com.cj.smartworker.business.member.dto.request.LoginRequest

fun interface LoginUseCase {
    fun login(loginRequest: LoginRequest): TokenResponse
}
