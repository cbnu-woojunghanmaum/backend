package com.cj.smartworker.domain.member.valueobject

@JvmInline
value class Month(val month: Int) {
    init {
        require(month in 1..12) { "월은 1~12 사이여야 합니다." }
    }
}

