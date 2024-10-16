package com.cj.smartworker.dataaccess.fcm.adapter

import com.cj.smartworker.annotation.PersistenceAdapter
import com.cj.smartworker.business.fcm.port.out.FindTokenPort
import com.cj.smartworker.business.fcm.port.out.SaveTokenPort
import com.cj.smartworker.dataaccess.fcm.mapper.toDomain
import com.cj.smartworker.dataaccess.fcm.mapper.toJpaEntity
import com.cj.smartworker.dataaccess.fcm.repository.DeviceTokenJpaRepository
import com.cj.smartworker.dataaccess.member.repository.MemberJpaRepository
import com.cj.smartworker.domain.fcm.entity.DeviceToken
import com.cj.smartworker.domain.fcm.valueobject.Token
import com.cj.smartworker.domain.member.entity.Member
import com.cj.smartworker.domain.member.valueobject.MemberId

@PersistenceAdapter
class DeviceTokenPersistenceAdapter(
    private val deviceTokenRepository: DeviceTokenJpaRepository,
): SaveTokenPort,
    FindTokenPort {
    override fun findByMemberIds(memberIds: List<MemberId>): Map<Token, Member> {
        val findByMemberIdIn = deviceTokenRepository.findByMemberIdIn(memberIds.map { it.id })
        return findByMemberIdIn.associate {
            it.token to it.toDomain().member
        }
    }

    override fun findByMemberId(memberId: MemberId): DeviceToken? {
        return deviceTokenRepository.findByMemberId(memberId)?.toDomain()
    }

    override fun saveToken(deviceToken: DeviceToken): DeviceToken {
        return deviceTokenRepository.save(deviceToken.toJpaEntity()).toDomain()
    }
}
