package com.cj.smartworker.business.member.port.out

import com.cj.smartworker.domain.member.valueobject.MemberId

fun interface HasNextMemberPort {
    fun hasNext(memberId: MemberId): Boolean
}
