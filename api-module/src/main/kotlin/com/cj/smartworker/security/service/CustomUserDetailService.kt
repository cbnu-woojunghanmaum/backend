package com.cj.smartworker.security.service

import com.cj.smartworker.business.member.port.out.FindMemberPort
import com.cj.smartworker.domain.member.exception.MemberDomainException
import com.cj.smartworker.domain.member.valueobject.LoginId
import com.cj.smartworker.domain.util.logger
import com.cj.smartworker.security.AccountAdapter
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailService(
    private val findMemberPort: FindMemberPort,
): UserDetailsService {
    private val logger = logger()
    override fun loadUserByUsername(username: String?): UserDetails {
        require(username != null) { "username is null or blank" }
        val member = findMemberPort.findByLoginId(LoginId(username)) ?: run {
                logger.info("해당 유저 이름의 정보를 찾을 수 없습니다. username: $username")
                throw MemberDomainException("해당 로그인 아이디의 유저를 찾을 수 없습니다.")
            }

        return AccountAdapter(member)
    }
}
