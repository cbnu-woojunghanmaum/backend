package com.cj.smartworker.business.day_report.dto.response

data class PagingResponse<T>(
    val nowPage: Long,
    val allPageCount: Long,
    val value: List<T>,
    val elementCount: Long,
)
