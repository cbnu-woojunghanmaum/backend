package com.cj.smartworker.domain.member.valueobject

import com.cj.smartworker.domain.member.exception.MemberDomainException
import com.cj.smartworker.domain.util.toKstLocalDateTime
import java.time.Instant

@JvmInline
value class Year(
    val year: Int,
) {
    init {
        val current = Instant.now().toKstLocalDateTime().year
        if (year !in 1900..current) {
            throw MemberDomainException("년도는 1900~현재 사이여야 합니다.")
        }
    }
}
