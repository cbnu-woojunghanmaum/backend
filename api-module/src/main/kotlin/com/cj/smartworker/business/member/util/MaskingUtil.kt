package com.cj.smartworker.business.member.util

import com.cj.smartworker.domain.member.valueobject.EmployeeName
import com.cj.smartworker.domain.member.valueobject.LoginId
import com.cj.smartworker.domain.member.valueobject.Phone

object MaskingUtil {

    /**
     * 010-1234-5678 -> 010-****-5678
     * 첫번 째 -를 인덱스 이후 데이터와 두번 째 - 인덱스 이전 데이터까지의 데이터를 *로 바꾼다.
     */
    fun maskPhone(phone: Phone): String {
        val phoneStr = phone.phone
        val firstDashIndex = phoneStr.indexOfFirst { it == '-' }
        val secondDashIndex = phoneStr.indexOf('-', firstDashIndex + 1)
        return phoneStr.replaceRange(firstDashIndex + 1 until secondDashIndex, "****")
    }

    /**
     * 아이디 : aaa*** <-마지막 3글자만 마스킹
     */
    fun maskLoginId(loginId: LoginId): String {
        val loginIdStr = loginId.loginId
        val length = loginIdStr.length
        val maskedLoginId = StringBuilder()

        if (length <= 6) {
            maskedLoginId.append(loginIdStr[0])
            maskedLoginId.append("*".repeat(length - 1))
        } else if (length <= 9) {
            maskedLoginId.append(loginIdStr.substring(0, 2))
            maskedLoginId.append("*".repeat(length - 2))
        } else {
            maskedLoginId.append(loginIdStr.substring(0, 3))
            maskedLoginId.append("*".repeat(length - 3))
        }

        return maskedLoginId.toString()
    }

    /**
     * 이름:  김xx <- 성씨만 노출
     */
    fun maskEmployeeName(employeeName: EmployeeName): String {
        val name = employeeName.employeeName
        return name.substring(0, 1) + "*".repeat(name.length - 1)
    }
}
