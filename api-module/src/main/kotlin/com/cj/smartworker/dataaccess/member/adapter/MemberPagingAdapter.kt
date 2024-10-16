package com.cj.smartworker.dataaccess.member.adapter

import com.cj.smartworker.annotation.PersistenceAdapter
import com.cj.smartworker.business.common.dto.request.CursorRequest
import com.cj.smartworker.business.member.dto.response.MemberPagingResponse
import com.cj.smartworker.business.member.port.out.HasNextMemberPort
import com.cj.smartworker.business.member.port.out.PagingMemberPort
import com.cj.smartworker.dataaccess.member.entity.QMemberJpaEntity.memberJpaEntity
import com.cj.smartworker.dataaccess.member.mapper.toMemberPagingResponse
import com.cj.smartworker.domain.member.valueobject.Deleted
import com.cj.smartworker.domain.member.valueobject.MemberId
import com.querydsl.jpa.impl.JPAQueryFactory

@PersistenceAdapter
internal class MemberPagingAdapter(
    private val queryFactory: JPAQueryFactory,
): PagingMemberPort,
    HasNextMemberPort {
    override fun hasNext(memberId: MemberId): Boolean {
        return queryFactory.select(memberJpaEntity)
            .from(memberJpaEntity)
            .where(
                memberJpaEntity.deleted.eq(Deleted.NOT_DELETED),
                memberJpaEntity.id.gt(memberId.id)
            )
            .fetchFirst() != null
    }

    override fun pagingMembers(cursorRequest: CursorRequest): List<MemberPagingResponse> {
        return queryFactory.select(memberJpaEntity)
            .from(memberJpaEntity)
            .where(
                memberJpaEntity.deleted.eq(Deleted.NOT_DELETED),
                memberJpaEntity.id.goe(cursorRequest.next)
            )
            .orderBy(memberJpaEntity.id.asc())
            .limit(cursorRequest.size.toLong())
            .fetch()
            .map { it.toMemberPagingResponse() }
    }
}
