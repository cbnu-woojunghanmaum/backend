package com.cj.smartworker.business.member.port.`in`

import com.cj.smartworker.business.heart_rate.dto.command.HeartRateCommand
import com.cj.smartworker.domain.member.entity.Member
import com.cj.smartworker.domain.member.valueobject.MemberId

fun interface UpdateHeartRateThresholdUseCase {
    fun update(command: HeartRateCommand, memberId: MemberId)
}
