package com.cj.smartworker.business.member.service

import com.cj.smartworker.business.member.dto.command.UpdateMemberCommand
import com.cj.smartworker.business.member.port.`in`.SaveMemberPort
import com.cj.smartworker.business.member.port.`in`.UpdateMemberUseCase
import com.cj.smartworker.business.member.port.out.FindMemberPort
import com.cj.smartworker.domain.heart_rate.value.HeartRateValue
import com.cj.smartworker.domain.member.entity.AuthorityEntity
import com.cj.smartworker.domain.member.entity.Member
import com.cj.smartworker.domain.member.exception.MemberDomainException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class UpdateMemberService(
    private val saveMemberPort: SaveMemberPort,
    private val findMemberPort: FindMemberPort,
): UpdateMemberUseCase {
    @Transactional
    override fun update(updateMemberCommand: UpdateMemberCommand): Member {
        val member = findMemberPort.findById(updateMemberCommand.memberId) ?: run {
            throw MemberDomainException("회원을 찾지 못하였습니다.")
        }
        member.apply {
            changePhone(updateMemberCommand.phone)
            changeGender(updateMemberCommand.gender)
            changeAuthority(setOf(AuthorityEntity(
                _authorityId = null,
                _authority = updateMemberCommand.authority,
            )))
            changeYear(updateMemberCommand.year)
            changeMonth(updateMemberCommand.month)
            changeDay(updateMemberCommand.day)
            changeEmployeeName(updateMemberCommand.employeeName)
            updateMemberCommand.email?.let { changeEmail(it) }
            changeHeartRateThreshold(HeartRateValue(updateMemberCommand.heartRateThreshold))
        }
        return saveMemberPort.saveMember(member)
    }
}
