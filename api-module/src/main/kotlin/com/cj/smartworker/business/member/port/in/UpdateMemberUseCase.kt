package com.cj.smartworker.business.member.port.`in`

import com.cj.smartworker.business.member.dto.command.UpdateMemberCommand
import com.cj.smartworker.domain.member.entity.Member

fun interface UpdateMemberUseCase {
    /**
     * return saved member
     */
    fun update(updateMemberCommand: UpdateMemberCommand): Member
}
