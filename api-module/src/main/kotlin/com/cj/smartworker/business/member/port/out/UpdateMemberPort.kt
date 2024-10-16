package com.cj.smartworker.business.member.port.out

import com.cj.smartworker.domain.member.entity.Member

fun interface UpdateMemberPort {
    fun update(member: Member)
}
