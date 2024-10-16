package com.cj.smartworker.dataaccess.member.adapter

import com.cj.smartworker.annotation.PersistenceAdapter
import com.cj.smartworker.business.member.dto.response.MemberPagingResponse
import com.cj.smartworker.business.member.port.`in`.SaveMemberPort
import com.cj.smartworker.business.member.port.out.*
import com.cj.smartworker.dataaccess.member.entity.AuthorityJpaEntity
import com.cj.smartworker.dataaccess.member.entity.QMemberJpaEntity.memberJpaEntity
import com.cj.smartworker.dataaccess.member.mapper.toDomain
import com.cj.smartworker.dataaccess.member.mapper.toDomainEntity
import com.cj.smartworker.dataaccess.member.mapper.toJpaEntity
import com.cj.smartworker.dataaccess.member.mapper.toMemberPagingResponse
import com.cj.smartworker.dataaccess.member.repository.AuthorityJpaRepository
import com.cj.smartworker.dataaccess.member.repository.MemberJpaRepository
import com.cj.smartworker.domain.member.entity.Member
import com.cj.smartworker.domain.member.valueobject.*
import com.cj.smartworker.domain.util.logger
import com.querydsl.jpa.impl.JPAQueryFactory

@PersistenceAdapter
internal class MemberPersistenceAdapter(
    private val queryFactory: JPAQueryFactory,
    private val memberJpaRepository: MemberJpaRepository,
    private val authorityJpaRepository: AuthorityJpaRepository,
) : SaveMemberPort,
    FindMemberPort,
    IsFirstMemberPort,
    SearchMemberPort,
    FindAdminPort,
    UpdateMemberPort {
    private val logger = logger()
    override fun saveMember(member: Member): Member {
        val authorityJpaEntities = mutableSetOf<AuthorityJpaEntity>()
        member.authorities.forEach { authority ->
            val authorityJpaEntity = (authorityJpaRepository.findByAuthority(authority.authority)
                ?: authorityJpaRepository.save(AuthorityJpaEntity(null, authority.authority)))
            authorityJpaEntities.add(authorityJpaEntity)
        }
        logger.info("member.toJpaEntity(): ${member.toJpaEntity().authorities.map {it.id}}")
        member.changeAuthority(authorityJpaEntities.map { it.toDomain() }.toSet())
        return memberJpaRepository.save(member.toJpaEntity()).toDomainEntity()
    }

    override fun findById(id: MemberId): Member? {
        return queryFactory.select(memberJpaEntity)
            .from(memberJpaEntity)
            .where(
                memberJpaEntity.id.eq(id.id)
                    .and(memberJpaEntity.deleted.eq(Deleted.NOT_DELETED))
            )
            .fetchOne()
            ?.let { return it.toDomainEntity() }
    }

    override fun findByLoginId(loginId: LoginId): Member? {
        return queryFactory.select(memberJpaEntity)
            .from(memberJpaEntity)
            .where(
                memberJpaEntity.loginId.eq(loginId.loginId)
                    .and(memberJpaEntity.deleted.eq(Deleted.NOT_DELETED))
            )
            .fetchOne()
            ?.let { return it.toDomainEntity() }
    }

    override fun findByEmail(email: Email): Member? {
        return queryFactory.select(memberJpaEntity)
            .from(memberJpaEntity)
            .where(
                memberJpaEntity.email.eq(email.email)
                    .and(memberJpaEntity.deleted.eq(Deleted.NOT_DELETED))
            )
            .fetchOne()?.toDomainEntity()
    }

    override fun isFirstMember(): Boolean {
        return queryFactory.select(memberJpaEntity)
            .from(memberJpaEntity)
            .where(memberJpaEntity.deleted.eq(Deleted.NOT_DELETED))
            .fetchFirst()
            ?.let { return false }
            ?: true
    }

    override fun searchByLoginId(loginId: LoginId): MemberPagingResponse? {
        return queryFactory.select(memberJpaEntity)
            .from(memberJpaEntity)
            .where(
                memberJpaEntity.loginId.eq(loginId.loginId),
                memberJpaEntity.deleted.eq(Deleted.NOT_DELETED),
            )
            .fetchOne()
            ?.let { return it.toMemberPagingResponse() }
    }

    override fun searchByLoginIdReturnMember(loginId: LoginId): Member? {
        return queryFactory.select(memberJpaEntity)
            .from(memberJpaEntity)
            .where(
                memberJpaEntity.loginId.eq(loginId.loginId),
                memberJpaEntity.deleted.eq(Deleted.NOT_DELETED),
            )
            .fetchOne()
            ?.toDomainEntity()
    }

    override fun findAdmins(): List<Member> {
        val memberJpaEntityIds = queryFactory.select(memberJpaEntity)
            .from(memberJpaEntity)
            .where(
                memberJpaEntity.authorities.any().authority.eq(Authority.ADMIN),
                memberJpaEntity.deleted.eq(Deleted.NOT_DELETED),
            )
            .orderBy(memberJpaEntity.createdAt.desc())
            .fetch()
        return memberJpaEntityIds.map { it.toDomainEntity() }
    }

    override fun findAdminsPagingResponse(): List<MemberPagingResponse> {
        return queryFactory.select(memberJpaEntity)
            .from(memberJpaEntity)
            .where(
                memberJpaEntity.authorities.any().authority.eq(Authority.ADMIN),
                memberJpaEntity.deleted.eq(Deleted.NOT_DELETED),
            )
            .orderBy(
                memberJpaEntity.employeeName.asc(),
                memberJpaEntity.createdAt.desc(),
            )
            .fetch()
            .map { it.toMemberPagingResponse() }
    }

    override fun update(member: Member) {
        memberJpaRepository.save(member.toJpaEntity())
    }
}
