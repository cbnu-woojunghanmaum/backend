package com.cj.smartworker.security

import com.cj.smartworker.domain.member.entity.Member
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class AccountAdapter(member: Member): User(
    member.loginId.loginId,
    member.password.password,
    member.authorities.map { GrantedAuthority { it.authority.role } }
)
