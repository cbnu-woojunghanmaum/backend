package com.cj.smartworker.domain.member.valueobject

import com.cj.smartworker.domain.member.exception.MemberDomainException
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class LoginIdTest {
    @Test
    fun `아이디는 4글자 이상 12글자 이하이어야 한다`() {
        assertThrows(IllegalArgumentException::class.java) {
            LoginId("A23")
        }.message.let {
            Assertions.assertThat(it).isEqualTo("아이디의 길이는 4 ~ 12글자여야합니다.")
        }

        assertThrows(IllegalArgumentException::class.java) {
            LoginId("1234567890123")
        }.message.let {
            Assertions.assertThat(it).isEqualTo("아이디의 길이는 4 ~ 12글자여야합니다.")
        }
    }

    @Test
    fun `숫자만 입력한 경우 IllegalArgumentException 예외를 던진다`() {
        assertThrows(IllegalArgumentException::class.java) {
            LoginId("1234567823")
        }.message.let {
            Assertions.assertThat(it).isEqualTo("아이디는 영어와 숫자 조합만 가능합니다.")
        }
    }

    @Test
    fun `영어만 입력한 경우 IllegalArgumentException 예외를 던진다`() {
        assertThrows(IllegalArgumentException::class.java) {
            LoginId("abcdefgh")
        }.message.let {
            Assertions.assertThat(it).isEqualTo("아이디는 영어와 숫자 조합만 가능합니다.")
        }
    }

    @Test
    fun `정상 생성 케이스`() {
        val loginId = LoginId("abcd1234")
        assertEquals("abcd1234", loginId.loginId)
    }

    @Test
    fun `정상 생성 케이스2`() {
        val loginId = LoginId("AD12")
        assertEquals("AD12", loginId.loginId)
    }

    @Test
    fun `한글을 입력하면 IllegalArgumentException 예외를 던진다`() {
        assertThrows(IllegalArgumentException::class.java) {
            LoginId("한글123")
        }.message.let {
            Assertions.assertThat(it).isEqualTo("아이디는 영어와 숫자 조합만 가능합니다.")
        }
    }
}


