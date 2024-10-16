package com.cj.smartworker.domain.member.valueobject

import com.cj.smartworker.domain.util.PasswordEncodeUtil

/**
 * 비밀빈호 8글자 이상, 영문 숫자 특수문자 모두 사용해야함
 * 특수문자: !@#$%^&*-
 * 중간에 whitespace가 있으면 안됨
 */
@JvmInline
value class Password private constructor(val password: String) {
    companion object {
        private const val SPECIAL_CHARACTERS = "!@#$%^&*-"

        operator fun invoke(value: String): Password {
            val trimmedPassword = value.trim()

            require(trimmedPassword.length in 8..20) {
                "비밀번호의 길이는 8 ~ 20글자여야합니다."
            }

            val containsLetter = trimmedPassword.any { it.isLetter() }
            val containsDigit = trimmedPassword.any { it.isDigit() }
            val containsSpecialChar = trimmedPassword.any { it in SPECIAL_CHARACTERS }
            val containsWhitespace = trimmedPassword.any { it.isWhitespace() }

            require(containsLetter && containsDigit && containsSpecialChar) {
                "비밀번호는 영어, 숫자, 특수문자를 포함해야합니다."
            }
            require(!containsWhitespace) {
                "비밀번호에 공백이 포함되어있습니다."
            }

            return Password(PasswordEncodeUtil.encode(trimmedPassword))
        }

        fun fromEncoded(encodedPassword: String): Password {
            return Password(encodedPassword)
        }
    }
}
