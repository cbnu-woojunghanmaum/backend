package com.cj.smartworker.business.member.service

import com.cj.smartworker.business.member.port.`in`.DeleteMemberUseCase
import com.cj.smartworker.business.member.port.`in`.SaveMemberPort
import com.cj.smartworker.business.member.port.out.FindMemberPort
import com.cj.smartworker.domain.member.exception.MemberDomainException
import com.cj.smartworker.domain.member.valueobject.MemberId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class DeleteMemberService(
    private val findMemberPort: FindMemberPort,
    private val saveMemberPort: SaveMemberPort,
): DeleteMemberUseCase {

    @Transactional
    override fun deleteByMemberId(memberId: MemberId) {
        val member = findMemberPort.findById(memberId) ?: run {
            throw MemberDomainException("회원을 찾지 못하였습니다.")
        }

        saveMemberPort.saveMember(member.delete())
    }
}
