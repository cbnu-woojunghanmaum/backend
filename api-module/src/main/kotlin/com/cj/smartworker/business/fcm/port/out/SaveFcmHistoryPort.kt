package com.cj.smartworker.business.fcm.port.out

import com.cj.smartworker.domain.fcm.valueobject.Emergency
import com.cj.smartworker.domain.member.entity.Member
import java.time.LocalDateTime

fun interface SaveFcmHistoryPort {
    fun saveFcmHistory(
        reporter: Member,
        admins: Set<Member>,
        createdAt: LocalDateTime,
        x: Float,
        y: Float,
        emergency: Emergency,
    )
}
