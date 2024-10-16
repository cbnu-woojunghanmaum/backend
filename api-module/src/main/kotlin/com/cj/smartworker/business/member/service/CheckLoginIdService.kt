package com.cj.smartworker.business.member.service

import com.cj.smartworker.business.member.port.`in`.CheckLoginIdUseCase
import com.cj.smartworker.business.member.port.out.FindMemberPort
import com.cj.smartworker.domain.member.valueobject.LoginId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class CheckLoginIdService(
    private val findMemberPort: FindMemberPort,
): CheckLoginIdUseCase {

    @Transactional(readOnly = true)
    override fun checkLoginId(loginId: LoginId): Boolean {
        findMemberPort.findByLoginId(loginId)?.let { return false }
        return true
    }
}
