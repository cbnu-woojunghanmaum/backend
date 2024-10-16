package com.cj.smartworker.domain.member.valueobject

enum class Authority(str: String) {
    ADMIN("ROLE_ADMIN"),
    EMPLOYEE("ROLE_EMPLOYEE");

    // ROLE을 반환한다.
    val role = str
}
