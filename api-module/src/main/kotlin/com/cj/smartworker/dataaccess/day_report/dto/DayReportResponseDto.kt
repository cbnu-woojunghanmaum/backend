package com.cj.smartworker.dataaccess.day_report.dto

import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDateTime

data class DayReportResponseDto @QueryProjection constructor(
    val id: Long,
    val memberName: String,
    val moveWork: Int,
    val heartRate: String,
    val km: Double,
    val createdAt: LocalDateTime,
    val isOver: Boolean,
)
