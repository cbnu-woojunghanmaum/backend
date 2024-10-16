package com.cj.smartworker.business.fcm.port.out

import com.cj.smartworker.domain.fcm.entity.DeviceToken
import com.cj.smartworker.domain.fcm.valueobject.Token
import com.cj.smartworker.domain.member.entity.Member
import com.cj.smartworker.domain.member.valueobject.MemberId

interface FindTokenPort {
    fun findByMemberIds(memberIds: List<MemberId>): Map<Token, Member>

    fun findByMemberId(memberId: MemberId): DeviceToken?
}
