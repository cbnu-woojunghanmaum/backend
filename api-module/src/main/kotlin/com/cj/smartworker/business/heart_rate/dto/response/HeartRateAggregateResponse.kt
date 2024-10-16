package com.cj.smartworker.business.heart_rate.dto.response

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable
import java.time.LocalDateTime

@Schema(description = "심박수 통계(해당 시간대의 30분간의)")
data class HeartRateAggregateResponse(
    @Schema(description = "평균")
    val averageHeartRate: Float,
    @Schema(description = "최고")
    val maxHeartRate: Int,
    @Schema(description = "최저")
    val minHeartRate: Int,
    @JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @Schema(description = "시간", example = "2024-07-18 14:30:00")
    val dateTime: LocalDateTime,
): Serializable
