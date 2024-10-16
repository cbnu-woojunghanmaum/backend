package com.cj.smartworker.application.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "공통 응답")
data class GenericResponse<T>(
    @Schema(description = "응답 데이터")
    val data: T,
    @Schema(description = "상태 코드")
    val statusCode: Int = 200,
    @Schema(description = "메시지 목록")
    val messages: List<String?> = mutableListOf(),
    @Schema(description = "성공 여부")
    val success: Boolean = true,
)
