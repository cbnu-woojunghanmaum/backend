package com.cj.smartworker.business.day_report.port.`in`

import com.cj.smartworker.business.day_report.dto.response.DayReportResponse
import com.cj.smartworker.business.day_report.dto.response.PagingResponse
import com.cj.smartworker.domain.day_report.entity.ReportFilter
import com.cj.smartworker.domain.day_report.entity.ReportSorting

interface PagingDayUseCase {
    fun paging(
        page: Long,
        offset: Long,
        reportSorting: List<ReportSorting>,
        reportFilter: List<ReportFilter>,
    ): PagingResponse<DayReportResponse>
}
