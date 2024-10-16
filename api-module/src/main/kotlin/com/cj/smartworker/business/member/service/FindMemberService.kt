package com.cj.smartworker.business.member.service

import com.cj.smartworker.business.member.mapper.MemberResponseMapper
import com.cj.smartworker.business.member.port.`in`.FindMemberUseCase
import com.cj.smartworker.business.member.port.out.FindMemberPort
import com.cj.smartworker.domain.member.entity.Member
import com.cj.smartworker.domain.member.valueobject.MemberId
import com.cj.smartworker.business.member.dto.response.MemberResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class FindMemberService(
    private val findMemberPort: FindMemberPort,
    private val memberResponseMapper: MemberResponseMapper,
): FindMemberUseCase {

    @Transactional(readOnly = true)
    override fun findMember(memberId: MemberId): MemberResponse {
        val member: Member = findMemberPort.findById(memberId) ?: run {
            throw IllegalArgumentException("해당 회원을 찾지 못했습니다.")
        }
        return memberResponseMapper.memberToMemberResponse(member)
    }
}
