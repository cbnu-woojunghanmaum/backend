package com.cj.smartworker.dataaccess.fcm.repository

import com.cj.smartworker.dataaccess.fcm.entity.DeviceTokenJpaEntity
import com.cj.smartworker.domain.member.valueobject.MemberId
import org.springframework.data.jpa.repository.JpaRepository

interface DeviceTokenJpaRepository: JpaRepository<DeviceTokenJpaEntity, Long>{
    fun findByMemberId(memberId: MemberId): DeviceTokenJpaEntity?
    fun findByMemberIdIn(memberIds: List<Long>): List<DeviceTokenJpaEntity>
}
