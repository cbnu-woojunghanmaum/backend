package com.cj.smartworker.business.member.port.`in`

import com.cj.smartworker.domain.member.valueobject.LoginId

/**
 * 로그인 아이디 중복 체크 유즈케이스
 */
fun interface CheckLoginIdUseCase {
    /**
     * 로그인 아이디 중복 체크
     * @param loginId 로그인 아이디
     * @return 중복 여부
     * 중복이면 false, 중복이 아니면 true
     */
    fun checkLoginId(loginId: LoginId): Boolean
}
