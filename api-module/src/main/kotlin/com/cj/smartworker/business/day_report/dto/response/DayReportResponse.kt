package com.cj.smartworker.business.day_report.dto.response

import com.fasterxml.jackson.annotation.JsonFormat
import com.querydsl.core.annotations.QueryProjection
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(name = "신기능 리포트")
data class DayReportResponse @QueryProjection constructor(
    val id: Long,
    val memberName: String,
    val moveWork: Int,
    val heartRate: Double,
    val km: Double,
    @JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "yyyy-MM-dd HH:mm:ss"
    )
    val createdAt: LocalDateTime,
    val isOverHeartRate: Boolean,
)
