package com.cj.smartworker.domain.member

import com.cj.smartworker.business.member.util.MaskingUtil
import com.cj.smartworker.domain.member.valueobject.EmployeeName
import com.cj.smartworker.domain.member.valueobject.LoginId
import com.cj.smartworker.domain.member.valueobject.Phone
import com.cj.smartworker.domain.util.HeartRateEncodingUtil
import com.cj.smartworker.domain.util.PasswordEncodeUtil
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class MakingUtilTest {
    @Test
    fun `휴대폰 번호 마스킹 테스트`() {
        val phoneNumber = "010-1234-5678"
        val phone = Phone(phoneNumber)
        val maskPhone = MaskingUtil.maskPhone(phone)
        Assertions.assertThat(maskPhone).isEqualTo("010-****-5678")
    }

    @Test
    fun `로그인 아이디 마스킹 테스트`() {
        // 6글자 이하
        val maskedLoginId1 = MaskingUtil.maskLoginId(LoginId("aaan1"))
        Assertions.assertThat(maskedLoginId1).isEqualTo("a****")

        // 9글자 이하
        val maskedLoginId2 = MaskingUtil.maskLoginId(LoginId("aaanb1234"))
        Assertions.assertThat(maskedLoginId2).isEqualTo("aa*******")

        // 12글자 이하
        val maskedLoginId3 = MaskingUtil.maskLoginId(LoginId("1234567890ab"))
        Assertions.assertThat(maskedLoginId3).isEqualTo("123*********")
    }

    @Test
    fun `회원 이름 마스킹 테스트`() {
        val maskEmployeeName = MaskingUtil.maskEmployeeName(EmployeeName("김철수"))
        Assertions.assertThat(maskEmployeeName).isEqualTo("김**")
        val maskEmployeeName2 = MaskingUtil.maskEmployeeName(EmployeeName("김구"))
        Assertions.assertThat(maskEmployeeName2).isEqualTo("김*")
    }

    @Test
    fun test() {
        val str = "sdfsdfsdf@"
        val encode = PasswordEncodeUtil.encode(str)
        val ss = PasswordEncodeUtil.matches(str, encode)
        println("test: ${ss}")
    }

}
