package com.cj.smartworker.business.member.service

import com.cj.smartworker.business.heart_rate.dto.command.HeartRateCommand
import com.cj.smartworker.business.member.port.`in`.UpdateHeartRateThresholdUseCase
import com.cj.smartworker.business.member.port.out.FindMemberPort
import com.cj.smartworker.business.member.port.out.UpdateMemberPort
import com.cj.smartworker.domain.heart_rate.value.HeartRateValue
import com.cj.smartworker.domain.member.entity.Member
import com.cj.smartworker.domain.member.valueobject.MemberId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class UpdateHeartRateThresholdService(
    private val findMemberPort: FindMemberPort,
    private val updateMemberPort: UpdateMemberPort,
): UpdateHeartRateThresholdUseCase {
    @Transactional
    override fun update(command: HeartRateCommand, memberId: MemberId) {
        val member: Member = findMemberPort.findById(memberId) ?: run {
            throw IllegalArgumentException("해당 직원을 찾을 수 없습니다.")
        }
        member.changeHeartRateThreshold(HeartRateValue(command.heartRate))
        updateMemberPort.update(member)
    }
}
