package com.cj.smartworker.business.day_report.service

import com.cj.smartworker.business.day_report.dto.response.DayReportResponse
import com.cj.smartworker.business.day_report.dto.response.PagingResponse
import com.cj.smartworker.business.day_report.port.`in`.PagingDayUseCase
import com.cj.smartworker.business.day_report.port.out.PagingDayReportPort
import com.cj.smartworker.domain.day_report.entity.ReportFilter
import com.cj.smartworker.domain.day_report.entity.ReportSorting
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DayReportService(
    private val pagingDayReportPort: PagingDayReportPort,
): PagingDayUseCase {

    @Transactional(readOnly = true)
    override fun paging(
        page: Long,
        offset: Long,
        reportSorting: List<ReportSorting>,
        reportFilter: List<ReportFilter>,
    ): PagingResponse<DayReportResponse> {
        if (page < 1) throw IllegalArgumentException("page는 1부터 시작해야 합니다.")
        if (offset < 1) throw IllegalArgumentException("offset은 1부터 시작해야 합니다.")

        val paging = pagingDayReportPort.paging(
            page = page,
            offset = offset,
            reportSorting = reportSorting,
            reportFilter = reportFilter,
        )
        val countPage = (pagingDayReportPort.countPage(reportFilter) / offset) + 1
        return PagingResponse(
            nowPage = page,
            allPageCount = countPage,
            value = paging,
            elementCount = paging.size.toLong(),
        )
    }
}
