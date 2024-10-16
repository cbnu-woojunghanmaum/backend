package com.cj.smartworker.dataaccess.member.mapper

import com.cj.smartworker.dataaccess.member.entity.AuthorityJpaEntity
import com.cj.smartworker.domain.member.entity.AuthorityEntity
import com.cj.smartworker.domain.member.valueobject.AuthorityId

fun AuthorityJpaEntity.toDomain(): AuthorityEntity = let {
    AuthorityEntity(
        _authorityId = AuthorityId(it.id!!),
        _authority = it.authority,
    )
}
