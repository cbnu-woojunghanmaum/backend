package com.cj.smartworker.fixture

import com.cj.smartworker.business.member.dto.command.SignupCommand
import com.cj.smartworker.domain.member.valueobject.Gender

class MemberFixture {
    companion object {
        fun createSignupCommand(): SignupCommand {
            return SignupCommand(
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
        }
    }
}
