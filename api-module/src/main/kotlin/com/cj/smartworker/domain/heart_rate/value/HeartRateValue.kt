package com.cj.smartworker.domain.heart_rate.value

@JvmInline
 value class HeartRateValue(val value: Int) {
    init {
        require(value in 0..280) { "심박수는 0 에서 280 사이여야 합니다." }
    }
 }
