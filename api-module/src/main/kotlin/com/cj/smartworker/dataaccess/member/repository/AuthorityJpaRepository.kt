package com.cj.smartworker.dataaccess.member.repository

import com.cj.smartworker.dataaccess.member.entity.AuthorityJpaEntity
import com.cj.smartworker.domain.member.valueobject.Authority
import org.springframework.data.jpa.repository.JpaRepository

interface AuthorityJpaRepository : JpaRepository<AuthorityJpaEntity, Long> {
    fun findByAuthority(authority: Authority): AuthorityJpaEntity?
}
