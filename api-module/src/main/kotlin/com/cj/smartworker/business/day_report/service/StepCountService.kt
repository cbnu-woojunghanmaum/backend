package com.cj.smartworker.business.day_report.service

import com.cj.smartworker.kafka.model.StepCountRequest
import com.cj.smartworker.business.day_report.port.`in`.StepCountUseCase
import com.cj.smartworker.business.day_report.port.out.StepCountMessagePort
import com.cj.smartworker.domain.member.entity.Member
import com.cj.smartworker.domain.util.toKstLocalDateTime
import com.cj.smartworker.messaging.mapper.KOREA_OFFSET
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.ZoneOffset

@Service
internal class StepCountService(
    private val stepCountMessagePort: StepCountMessagePort,
): StepCountUseCase {
    override fun sendStepCount(member: Member, step: Int) {
        stepCountMessagePort.sendStepPublish(
            StepCountRequest(
                memberId = member.id!!.id,
                step = step,
                timestamp = Instant.now().toKstLocalDateTime().toEpochSecond(ZoneOffset.of(KOREA_OFFSET)),
            )
        )
    }
}
