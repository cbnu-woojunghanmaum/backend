package com.cj.smartworker.dataaccess.member.mapper

import com.cj.smartworker.dataaccess.member.entity.AuthorityJpaEntity
import com.cj.smartworker.dataaccess.member.entity.MemberJpaEntity
import com.cj.smartworker.domain.heart_rate.value.HeartRateValue
import com.cj.smartworker.domain.member.entity.AuthorityEntity
import com.cj.smartworker.domain.member.entity.Member
import com.cj.smartworker.domain.member.valueobject.*

fun Member.toJpaEntity(): MemberJpaEntity = let {
    MemberJpaEntity(
        id = it.memberId?.id,
        email = it.email?.email,
        password = it.password.password,
        loginId = it.loginId.loginId,
        phone = it.phone.phone,
        authorities = it.authorities.map { authority -> AuthorityJpaEntity(
            id = authority.authorityId?.authorityId,
            authority = authority.authority,
        ) }.toSet(),
        deleted = it.deleted,
        createdAt = it.createdAt,
        gender = it.gender,
        employeeName = it.employeeName.employeeName,
        year = it.year.year,
        month = it.month.month,
        day = it.day.day,
        heartRateThreshold = it.heartRateThreshold.value,
    )
}

fun MemberJpaEntity.toDomainEntity(): Member = let {
    Member(
        _memberId = it.id?.let { id -> MemberId(id) },
        _loginId = LoginId(it.loginId),
        _password = Password.fromEncoded(it.password),
        _email = it.email?.let { email -> Email(email) },
        _phone = Phone(it.phone),
        _authorities = it.authorities.map { authority -> AuthorityEntity(
            _authorityId = AuthorityId(it.id!!),
            _authority = authority.authority,
        ) }.toSet(),
        _deleted = it.deleted,
        _createdAt = it.createdAt,
        _gender = it.gender,
        _employeeName = EmployeeName(it.employeeName),
        _year = Year(it.year),
        _month = Month(it.month),
        _day = Day(it.day),
        _heartRateThreshold = HeartRateValue(it.heartRateThreshold),
    )
}
