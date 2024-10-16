package com.cj.smartworker.application.day_report

import com.cj.smartworker.annotation.WebAdapter
import com.cj.smartworker.application.response.GenericResponse
import com.cj.smartworker.business.day_report.dto.response.DayReportResponse
import com.cj.smartworker.business.day_report.service.SearchService
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
class SearchByMemberNameController(
    private val searchService: SearchService,
) {


    @Operation(
        summary = "검색 [Admin]",
        description = "검색 [Admin]",
        parameters = [
            Parameter(name = "name", description = "이름", required = true, example = "홍길"),
        ]
    )
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/search")
    fun searchByName(
        @RequestParam("name") name: String,
    ): GenericResponse<List<DayReportResponse>> {
        val searchByName = searchService.searchByName(name)
        return GenericResponse(
            data = searchByName,
            statusCode = HttpStatus.OK.value(),
            success = true,
        )
    }
}
