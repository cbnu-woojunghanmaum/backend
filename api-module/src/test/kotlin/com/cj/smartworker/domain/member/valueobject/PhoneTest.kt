package com.cj.smartworker.domain.member.valueobject

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PhoneTest {
    @Test
    fun `잘못된 휴대폰 번호를 넣은 경우 IllegalArgumentException 에외를 던진다`() {
        val invalidPhoneNumber = "010-1234-567p"
        assertThrows(IllegalArgumentException::class.java) {
            Phone(invalidPhoneNumber)
        }.message.let {
            assertEquals("전화번호는 숫자와 '-'만 가능합니다. Ex: 010-1234-1234", it)
        }
    }

    @Test
    fun `정상적으로 입력한 경우`() {
        val phoneNumber = "010-1234-5678"
        assertDoesNotThrow {
            Phone(phoneNumber)
        }
    }
}
