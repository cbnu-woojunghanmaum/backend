package com.cj.smartworker.business.heart_rate.service

import com.cj.smartworker.business.heart_rate.dto.command.HeartRateCommand
import com.cj.smartworker.business.heart_rate.port.`in`.SaveHeartRateUseCase
import com.cj.smartworker.business.heart_rate.port.out.SendHeartRateMessagePort
import com.cj.smartworker.domain.heart_rate.entity.HeartRate
import com.cj.smartworker.domain.heart_rate.value.HeartRateValue
import com.cj.smartworker.domain.member.entity.Member
import com.cj.smartworker.domain.util.toKstLocalDateTime
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.UUID

@Service
internal class SaveHeartRateService(
    private val sendHeartRateMessagePort: SendHeartRateMessagePort,
): SaveHeartRateUseCase {
    override fun save(command: HeartRateCommand, member: Member) {
        val heartRate = HeartRate(
            _heartRateId = UUID.randomUUID(),
            _memberId = member.memberId!!,
            _value = HeartRateValue(command.heartRate),
            _timestamp = Instant.now().toKstLocalDateTime(),
        )
        sendHeartRateMessagePort.publish(heartRate)
    }
}
