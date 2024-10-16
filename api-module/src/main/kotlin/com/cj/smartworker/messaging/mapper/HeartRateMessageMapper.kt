package com.cj.smartworker.messaging.mapper

import com.cj.smartworker.domain.heart_rate.entity.HeartRate
import com.cj.smartworker.kafka.model.HeartRateDto
import org.springframework.stereotype.Component
import java.time.ZoneOffset

@Component
class HeartRateMessageMapper {
    fun toDto(heartRate: HeartRate): HeartRateDto {
        return HeartRateDto(
            memberId = heartRate.memberId.id,
            heartRate = heartRate.value.value,
            timestamp = heartRate.timestamp.toEpochSecond(ZoneOffset.of(KOREA_OFFSET)),
        )
    }
}

const val KOREA_OFFSET = "+09:00"
