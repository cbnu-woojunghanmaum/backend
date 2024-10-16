package com.cj.smartworker.business.member.service

import com.cj.smartworker.business.member.dto.command.SignupCommand
import com.cj.smartworker.business.member.port.`in`.SaveMemberPort
import com.cj.smartworker.business.member.port.`in`.SignupUseCase
import com.cj.smartworker.business.member.port.out.FindMemberPort
import com.cj.smartworker.business.member.port.out.IsFirstMemberPort
import com.cj.smartworker.domain.heart_rate.value.HeartRateValue
import com.cj.smartworker.domain.member.entity.AuthorityEntity
import com.cj.smartworker.domain.member.entity.Member
import com.cj.smartworker.domain.member.exception.MemberDomainException
import com.cj.smartworker.domain.member.valueobject.*
import com.cj.smartworker.domain.util.toKstLocalDateTime
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
internal class SignupService(
    private val isFirstMemberPort: IsFirstMemberPort,
    private val findMemberPort: FindMemberPort,
    private val saveMemberPort: SaveMemberPort,
) : SignupUseCase {

    /**
     * 실제 production에선 휴대폰 인증 추가 필요
     */
    @Transactional
    override fun signup(command: SignupCommand): MemberId {
        findMemberPort.findByLoginId(LoginId(command.loginId))
            ?.let {
                throw MemberDomainException("이미 가입된 회원입니다.")
            }
        if (command.email != null) {
            findMemberPort.findByEmail(Email(command.email)) ?.let {
                    throw MemberDomainException("이미 존재하는 이메일 입니다.")
                }
        }

        val isFirst = isFirstMemberPort.isFirstMember()

        val authority = if (isFirst) Authority.ADMIN else Authority.EMPLOYEE
        val age = Instant.now().toKstLocalDateTime().year - command.year + 1
        val heartRateThreshold = if (command.gender == Gender.MALE && age < 40) {
            // The HUNT formula (men and women who are active): 211 - (0.64 x age)
            HeartRateValue(211 - (0.64 * age).toInt())
        } else if (command.gender == Gender.FEMALE && age < 40) {
            // Gulati formula (women only): 206 - (0.88 × age)
            HeartRateValue(206 - (0.88 * age).toInt())
        } else {
            // Tanaka formula(men and women over age 40): 208 - (0.7 × age)
            HeartRateValue(208 - (0.7 * age).toInt())
        }

        return Member(
            _memberId = null,
            _loginId = LoginId(command.loginId),
            _password = Password(command.password),
            _gender = command.gender,
            _createdAt = Instant.now().toKstLocalDateTime(),
            _deleted = Deleted.NOT_DELETED,
            _authorities = setOf(AuthorityEntity(
                _authorityId = null,
                _authority = authority,
            )),
            _email = command.email?.let { Email(it) },
            _employeeName = EmployeeName(command.employeeName),
            _year = Year(command.year),
            _month = Month(command.month),
            _day = Day(command.day),
            _phone = Phone(command.phone),
            _heartRateThreshold = heartRateThreshold,
        ).let {
            saveMemberPort.saveMember(it).memberId!!
        }
    }
}
