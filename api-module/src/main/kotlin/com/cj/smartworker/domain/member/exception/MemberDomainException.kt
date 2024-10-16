package com.cj.smartworker.domain.member.exception

import com.cj.smartworker.domain.common.DomainException

data class MemberDomainException(
    override val message: String?
) : DomainException(message)
