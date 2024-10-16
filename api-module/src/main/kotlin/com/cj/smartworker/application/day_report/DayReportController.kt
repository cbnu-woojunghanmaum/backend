package com.cj.smartworker.application.day_report

import com.cj.smartworker.annotation.WebAdapter
import com.cj.smartworker.application.response.GenericResponse
import com.cj.smartworker.business.day_report.dto.response.DayReportResponse
import com.cj.smartworker.business.day_report.dto.response.PagingResponse
import com.cj.smartworker.business.day_report.service.DayReportService
import com.cj.smartworker.domain.day_report.entity.ReportFilter
import com.cj.smartworker.domain.day_report.entity.ReportSorting
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@Tag(name = "본선 신기능", description = "본선 신기능.")
@WebAdapter
@RestController
@RequestMapping("/api/v1/reporting")
class DayReportController(
    private val dayReportService: DayReportService,
) {

    @Operation(
        summary = "신기능 리포트 정렬 페이징 조회[Admin]",
        description = "신기능 리포트 정렬 페이징 조회 [Admin]",
        parameters = [
            Parameter(name = "page", description = "현재 페이지 1부터 시작", required = true, example = "1"),
            Parameter(name = "offset", description = "item 개수", required = true, example = "10"),
        ]
    )
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/day-report")
    fun pagingDayReport(
        @RequestParam("page") page: Long,
        @RequestParam("offset") offset: Long,
        @RequestParam("report-sorting") reportSorting: List<ReportSorting>,
        @RequestParam("report-condition") reportCondition: List<ReportFilter>,
    ): GenericResponse<PagingResponse<DayReportResponse>> {
        val paging = dayReportService.paging(
            page = page,
            offset = offset,
            reportSorting = reportSorting,
            reportFilter = reportCondition,
        )
        return GenericResponse(
            data = paging,
            statusCode = HttpStatus.OK.value(),
            success = true,
        )
    }
}
