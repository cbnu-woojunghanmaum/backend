package com.cj.smartworker.business.day_report.port.`in`

import com.cj.smartworker.domain.member.entity.Member

interface StepCountUseCase {
    fun sendStepCount(member: Member, step: Int)
}
