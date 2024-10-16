package com.cj.smartworker.domain.fcm.entity

import com.cj.smartworker.domain.common.BaseEntity
import com.cj.smartworker.domain.member.valueobject.DeviceTokenId
import com.cj.smartworker.domain.fcm.valueobject.Token
import com.cj.smartworker.domain.member.entity.Member

class DeviceToken(
    private val _deviceTokenId: DeviceTokenId?,
    private val _member: Member,
    private var _token: Token,
): BaseEntity<DeviceTokenId?>(_deviceTokenId){
    val deviceTokenId: DeviceTokenId?
        get() = _deviceTokenId
    val member: Member
        get() = _member

    val token: Token
        get() = _token

    fun updateToken(token: Token): DeviceToken {
        _token = token
        return this
    }
}
