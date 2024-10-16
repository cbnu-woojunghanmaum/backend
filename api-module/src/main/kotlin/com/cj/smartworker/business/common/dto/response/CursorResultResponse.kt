package com.cj.smartworker.business.common.dto.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Cursor Paging 결과 응답")
data class CursorResultResponse<T>(
    @Schema(description = "결과 값")
    val value: List<T>,
    @Schema(description = "다음 페이지 존재 여부")
    val hasNext: Boolean,
    @Schema(description = "마지막 인덱스")
    val lastIndex: Long?,
)
