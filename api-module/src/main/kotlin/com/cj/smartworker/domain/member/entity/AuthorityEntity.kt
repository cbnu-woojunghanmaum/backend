package com.cj.smartworker.domain.member.entity

import com.cj.smartworker.domain.common.BaseEntity
import com.cj.smartworker.domain.member.valueobject.Authority
import com.cj.smartworker.domain.member.valueobject.AuthorityId

class AuthorityEntity(
    private val _authorityId: AuthorityId?,
    private val _authority: Authority,
): BaseEntity<AuthorityId?>(_authorityId) {
    val authorityId: AuthorityId?
        get() = _authorityId

    val authority: Authority
        get() = _authority
}
