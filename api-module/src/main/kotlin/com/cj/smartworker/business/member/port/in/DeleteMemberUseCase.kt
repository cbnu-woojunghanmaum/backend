package com.cj.smartworker.business.member.port.`in`

import com.cj.smartworker.domain.member.valueobject.MemberId

/**
 * 관리자용 API
 * 유저를 삭제합니다.
 * Soft delete를 사용합니다.
 */
fun interface DeleteMemberUseCase {
    fun deleteByMemberId(memberId: MemberId)
}
