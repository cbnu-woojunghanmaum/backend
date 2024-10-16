package com.cj.smartworker.domain.member.valueobject


/**
 * 아이디 4~12 글자, 영어와 숫자 조합만 가능
 */
@JvmInline
value class LoginId(val loginId: String) {

    init {
        require(loginId.length in 4..12) {
            "아이디의 길이는 4 ~ 12글자여야합니다."
        }
        require(regex.matches(loginId)) {
            "아이디는 영어와 숫자 조합만 가능합니다."
        }
        require(loginId.any { it.isLetter() } && loginId.any { it.isDigit() }) {
            "아이디는 영어와 숫자 조합만 가능합니다."
        }
    }

    companion object {
        private val regex = "^[a-zA-Z0-9]*$".toRegex()
    }
}
