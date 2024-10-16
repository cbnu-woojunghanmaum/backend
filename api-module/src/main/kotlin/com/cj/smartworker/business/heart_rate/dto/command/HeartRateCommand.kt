package com.cj.smartworker.business.heart_rate.dto.command

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "심박수 저장")
data class HeartRateCommand(
    @Schema(description = "심박수", example = "80")
    val heartRate: Int,
)
