package com.cj.smartworker.business.member.port.`in`

import com.cj.smartworker.domain.member.entity.Member

fun interface SaveMemberPort {
    fun saveMember(member: Member): Member
}
