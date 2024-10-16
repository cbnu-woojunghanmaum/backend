package com.cj.smartworker.dataaccess.fcm.mapper

import com.cj.smartworker.dataaccess.fcm.entity.DeviceTokenJpaEntity
import com.cj.smartworker.dataaccess.member.entity.AuthorityJpaEntity
import com.cj.smartworker.dataaccess.member.mapper.toDomainEntity
import com.cj.smartworker.dataaccess.member.mapper.toJpaEntity
import com.cj.smartworker.domain.fcm.entity.DeviceToken
import com.cj.smartworker.domain.member.valueobject.DeviceTokenId

fun DeviceTokenJpaEntity.toDomain(): DeviceToken = let {
    DeviceToken(
        _deviceTokenId = it.id?.let { id -> DeviceTokenId(id) },
        _member = it.member.toDomainEntity(),
        _token = it.token,
    )
}

fun DeviceToken.toJpaEntity(): DeviceTokenJpaEntity = let {
    DeviceTokenJpaEntity(
        id = it.deviceTokenId?.deviceTokenId,
        member = it.member.toJpaEntity(),
        token = it.token,
    )
}
