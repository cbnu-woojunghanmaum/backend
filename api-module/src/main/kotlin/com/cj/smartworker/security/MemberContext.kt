package com.cj.smartworker.security

import com.cj.smartworker.domain.member.entity.Member

object MemberContext {
    private val memberContext = ThreadLocal<Member>()

    var MEMBER: Member
        get() = memberContext.get()
        set(member) {
            memberContext.set(member)
        }

    fun clear() {
        memberContext.remove()
    }
}
