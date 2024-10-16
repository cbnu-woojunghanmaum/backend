package com.cj.smartworker.business.member.port.out

import com.cj.smartworker.domain.member.valueobject.Phone
import java.time.LocalDateTime

fun interface FindPhoneApprovalHistoryPort {

    /**
     * dateTime 이후의 phone 인증번호 발생 회수를 반환한다.
     */
    fun findPhoneApprovalHistory(phone: Phone, dateTime: LocalDateTime): Int
}
