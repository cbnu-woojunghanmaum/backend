package com.cj.smartworker.business.member.service

import com.cj.smartworker.business.member.dto.command.SignupCommand
import com.cj.smartworker.business.member.dto.command.UpdateMemberCommand
import com.cj.smartworker.business.member.port.`in`.SignupUseCase
import com.cj.smartworker.business.member.port.`in`.UpdateMemberUseCase
import com.cj.smartworker.domain.member.valueobject.*
import com.cj.smartworker.testbase.IntegrationTestBase
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class UpdateMemberUseCaseTest @Autowired constructor(
    private val updateMemberUseCase: UpdateMemberUseCase,
    private val signupUseCase: SignupUseCase,
): IntegrationTestBase() {
    val signupCommand = SignupCommand(
        loginId = "abcd1234",
        password = "Abcd1234@",
        phone = "010-1234-5678",
        gender = Gender.MALE,
        email = null,
        employeeName = "홍길동",
        year = 2000,
        month = 1,
        day = 1
    )
    @Test
    fun `멤버 정보 수정 통합 테스트`() {
        // given
        val memberId = signupUseCase.signup(signupCommand)
        val year = Year(1999)
        val gender = Gender.FEMALE
        val phone = Phone("010-1234-1234")
        val email = Email("sss@gmail.com")
        val updateMemberCommand = UpdateMemberCommand(
            memberId = memberId,
            phone = phone,
            gender = gender,
            authority = Authority.EMPLOYEE,
            year = year,
            month = Month(12),
            day = Day(1),
            employeeName = EmployeeName("홍길순"),
            email = email,
            heartRateThreshold = 80,
        )
        // when
        val updated = updateMemberUseCase.update(updateMemberCommand)

        // then
        Assertions.assertThat(updated.email).isEqualTo(email)
        Assertions.assertThat(updated.phone).isEqualTo(phone)
        Assertions.assertThat(updated.gender).isEqualTo(gender)
        Assertions.assertThat(updated.year).isEqualTo(year)
    }
}
