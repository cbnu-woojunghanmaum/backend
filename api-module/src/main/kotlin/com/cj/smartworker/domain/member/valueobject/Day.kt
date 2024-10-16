package com.cj.smartworker.domain.member.valueobject

@JvmInline
value class Day(val day: Int) {
    init {
        require(day in 1..31) { "일은 1 ~ 31 사이여야합니다." }
    }
}
