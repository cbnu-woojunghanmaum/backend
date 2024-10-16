package com.cj.smartworker.business.member.port.out

import com.cj.smartworker.domain.member.entity.Member
import com.cj.smartworker.domain.member.valueobject.Email
import com.cj.smartworker.domain.member.valueobject.LoginId
import com.cj.smartworker.domain.member.valueobject.MemberId

interface FindMemberPort {
    fun findById(id: MemberId): Member?

    fun findByLoginId(loginId: LoginId): Member?

    fun findByEmail(email: Email): Member?
}
