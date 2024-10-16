package com.cj.smartworker.business.heart_rate.port.`in`

import com.cj.smartworker.business.heart_rate.dto.command.HeartRateCommand
import com.cj.smartworker.domain.member.entity.Member

fun interface SaveHeartRateUseCase {
    fun save(command: HeartRateCommand, member: Member)
}
