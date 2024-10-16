package com.cj.smartworker.domain.member.entity

import com.cj.smartworker.domain.common.RootAggregate
import com.cj.smartworker.domain.heart_rate.value.HeartRateValue
import com.cj.smartworker.domain.member.valueobject.*
import java.time.LocalDateTime


/**
 * 아이디 4~12 글자, 영어와 숫자 조합만 가능
 * 비밀빈호 8글자 이상, 영문 숫자 특수문자 모두 사용해야함
 */
class Member(
    private val _memberId: MemberId?,
    private val _loginId: LoginId,
    private val _password: Password,
    private var _phone: Phone,
    private val _createdAt: LocalDateTime,
    private var _gender: Gender,
    private var _email: Email?,
    private var _authorities: Set<AuthorityEntity>,
    private var _employeeName: EmployeeName,
    private var _deleted: Deleted,
    private var _year: Year,
    private var _month: Month,
    private var _day: Day,
    private var _heartRateThreshold: HeartRateValue
): RootAggregate<MemberId?>(_memberId) {
    val memberId: MemberId?
        get() = _memberId
    val loginId: LoginId
        get() = _loginId
    val password: Password
        get() = _password
    val phone: Phone
        get() = _phone
    val createdAt: LocalDateTime
        get() = _createdAt
    val gender: Gender
        get() = _gender
    val email: Email?
        get() = _email
    val authorities: Set<AuthorityEntity>
        get() = _authorities
    val deleted: Deleted
        get() = _deleted
    val employeeName: EmployeeName
        get() = _employeeName
    val year: Year
        get() = _year
    val month: Month
        get() = _month
    val day: Day
        get() = _day
    val heartRateThreshold: HeartRateValue
        get() = _heartRateThreshold

    fun changePhone(phone: Phone): Member {
        _phone = phone
        return this
    }
    fun changeGender(gender: Gender): Member {
        _gender = gender
        return this
    }
    fun changeAuthority(authorities: Set<AuthorityEntity>): Member {
        _authorities = authorities
        return this
    }
    fun changeYear(year: Year): Member {
        _year = year
        return this
    }
    fun changeMonth(month: Month): Member {
        _month = month
        return this
    }
    fun changeDay(day: Day): Member {
        _day = day
        return this
    }
    fun changeEmployeeName(employeeName: EmployeeName): Member {
        _employeeName = employeeName
        return this
    }
    fun changeEmail(email: Email?): Member {
        _email = email
        return this
    }
    fun delete(): Member {
        _deleted = Deleted.DELETED
        return this
    }
    fun changeHeartRateThreshold(heartRateThreshold: HeartRateValue): Member {
        _heartRateThreshold = heartRateThreshold
        return this
    }
}
