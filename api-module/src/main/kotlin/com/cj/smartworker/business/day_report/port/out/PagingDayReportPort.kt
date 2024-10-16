package com.cj.smartworker.business.day_report.port.out

import com.cj.smartworker.business.day_report.dto.response.DayReportResponse
import com.cj.smartworker.domain.day_report.entity.ReportFilter
import com.cj.smartworker.domain.day_report.entity.ReportSorting

interface PagingDayReportPort {
    fun paging(
        page: Long,
        offset: Long,
        reportSorting: List<ReportSorting>,
        reportFilter: List<ReportFilter>,
    ): List<DayReportResponse>

    fun countPage(reportFilter: List<ReportFilter>): Long
}
