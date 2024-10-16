package com.cj.smartworker.business.member.service

import com.cj.smartworker.business.member.port.`in`.SearchMemberUseCase
import com.cj.smartworker.business.member.port.`in`.SignupUseCase
import com.cj.smartworker.business.member.util.MaskingUtil
import com.cj.smartworker.domain.member.valueobject.EmployeeName
import com.cj.smartworker.domain.member.valueobject.LoginId
import com.cj.smartworker.testbase.IntegrationTestBase
import com.cj.smartworker.fixture.MemberFixture
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class SearchMemberUseCaseTest @Autowired constructor(
    private val searchMemberUseCase: SearchMemberUseCase,
    private val signupUseCase: SignupUseCase,
): IntegrationTestBase() {
    @Test
    fun `해당 loginId의 유저가 존재하면 찾은 유저의 정보를 반환한다`() {
        val signupCommand = MemberFixture.createSignupCommand()
        signupUseCase.signup(signupCommand)
        val loginId = signupCommand.loginId
        val memberResponse = searchMemberUseCase.searchByLoginId(LoginId(loginId))
        assertEquals(MaskingUtil.maskEmployeeName(EmployeeName(signupCommand.employeeName)), memberResponse.value[0].employeeName)
    }
}
