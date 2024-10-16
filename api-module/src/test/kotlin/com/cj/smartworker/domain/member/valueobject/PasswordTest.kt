package com.cj.smartworker.domain.member.valueobject

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PasswordTest {
    @Test
    fun `7글자인 경우에 IllegalArgumentException 예외를 던진다`() {
        val pass = "s".repeat(7)
        assertThrows(IllegalArgumentException::class.java) {
            Password(pass)
        }.message.let {
            assertEquals("비밀번호의 길이는 8 ~ 20글자여야합니다.", it)
        }
    }

    @Test
    fun `21글자인 경우에 IllegalArgumentException 예외를 던진다`() {
        val pass = "s".repeat(21)
        assertThrows(IllegalArgumentException::class.java) {
            Password(pass)
        }.message.let {
            assertEquals("비밀번호의 길이는 8 ~ 20글자여야합니다.", it)
        }
    }

    @Test
    fun `특수문자를 안넣은 경우 IllegalArgumentException 예외를 던진다`() {
        assertThrows(IllegalArgumentException::class.java) {
            Password("asdf1234")
        }.message.let {
            assertEquals("비밀번호는 영어, 숫자, 특수문자를 포함해야합니다.", it)
        }
    }

    @Test
    fun `숫자를 안넣은 경우 IllegalArgumentException 예외를 던진다`() {
        assertThrows(IllegalArgumentException::class.java) {
            Password("asdf@@!!")
        }.message.let {
            assertEquals("비밀번호는 영어, 숫자, 특수문자를 포함해야합니다.", it)
        }
    }

    @Test
    fun `영어를 안넣은 경우 IllegalArgumentException 예외를 던진다`() {
        assertThrows(IllegalArgumentException::class.java) {
            Password("1234@@!!")
        }.message.let {
            assertEquals("비밀번호는 영어, 숫자, 특수문자를 포함해야합니다.", it)
        }
    }

    @Test
    fun `공백을 넣은 경우 IllegalArgumentException 예외를 던진다`() {
        assertThrows(IllegalArgumentException::class.java) {
            Password("asdf 1234@@!!")
        }.message.let {
            assertEquals("비밀번호에 공백이 포함되어있습니다.", it)
        }
    }

    @Test
    fun `정상적으로 만든 경우`() {
        val password = Password("asdf1234@@!!")
        assertNotNull(password)
    }
}
