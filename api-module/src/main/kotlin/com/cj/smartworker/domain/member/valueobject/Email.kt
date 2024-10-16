package com.cj.smartworker.domain.member.valueobject

@JvmInline
value class Email(val email: String) {

    init {
        require(email.contains('@') && email.first() != '@' && email.last() != '@') {
            "잘못된 이메일 형식입니다."
        }
    }
}
