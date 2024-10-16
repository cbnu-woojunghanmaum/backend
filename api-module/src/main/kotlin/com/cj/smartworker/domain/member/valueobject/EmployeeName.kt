package com.cj.smartworker.domain.member.valueobject

@JvmInline
value class EmployeeName(val employeeName: String) {
    init {
        require(employeeName.length in 2..20) {
            "직원 이름은 2~20자여야 합니다."
        }
    }
}
