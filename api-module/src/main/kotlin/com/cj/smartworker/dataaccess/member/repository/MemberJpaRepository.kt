package com.cj.smartworker.dataaccess.member.repository

import com.cj.smartworker.dataaccess.member.entity.MemberJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MemberJpaRepository : JpaRepository<MemberJpaEntity, Long> {
}
