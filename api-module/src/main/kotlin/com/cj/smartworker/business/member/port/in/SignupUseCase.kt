package com.cj.smartworker.business.member.port.`in`

import com.cj.smartworker.business.member.dto.command.SignupCommand
import com.cj.smartworker.domain.member.valueobject.MemberId

fun interface SignupUseCase {
    /**
     * return member id
     */
    fun signup(command: SignupCommand): MemberId
}
