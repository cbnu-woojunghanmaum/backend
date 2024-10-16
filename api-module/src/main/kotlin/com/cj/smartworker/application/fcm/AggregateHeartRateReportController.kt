package com.cj.smartworker.application.fcm

import com.cj.smartworker.annotation.WebAdapter
import com.cj.smartworker.application.response.GenericResponse
import com.cj.smartworker.business.fcm.dto.request.GPSRange
import com.cj.smartworker.business.fcm.dto.response.HeartRateAggregateResponse
import com.cj.smartworker.business.fcm.port.`in`.AggregateHeartRateReportUseCase
import com.cj.smartworker.domain.fcm.valueobject.Emergency
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@Tag(name = "신고이력", description = "신고이력 조회 API 목록입니다.")
@WebAdapter
@RestController
@RequestMapping("/api/v1/fcm")
internal class AggregateHeartRateReportController(
    private val aggregateHeartRateReportUseCase: AggregateHeartRateReportUseCase,
) {

    @Operation(
        summary = "이상 심박수 지도용 집계 [Admin]",
        description = "이상 심박수 지도용 집계를 나타냅니다. [Admin]",
        parameters = [
            Parameter(name = "start", description = "심박수 집계 조회 시작 시간", required = true, example = "2021-01-01 00:00:00"),
            Parameter(name = "end", description = "심박수 집계 조회 끝나는 시간", required = true, example = "2021-01-01 23:59:59"),
            Parameter(
                name = "gps-range",
                description = "SMALL = 10m x 10m 범위, LARGE 100m x 100m 범위",
                required = true,
                example = "SMALL or LARGE",
            ),
            Parameter(name = "emergency", description = "신고 타입", required = true, example = "HEART_RATE or REPORT")
        ]
    )
    @ApiResponse(responseCode = "200", description = "집계 성공")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/aggregate-heart-report")
    fun aggregateHeartRateReport(
        @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") start: LocalDateTime,
        @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") end: LocalDateTime,
        @RequestParam("gps-range") gpsRange: GPSRange,
        @RequestParam("emergency") emergency: Emergency,
    ): GenericResponse<List<HeartRateAggregateResponse>> {
        val aggregate = aggregateHeartRateReportUseCase.aggregate(
            start = start,
            end = end,
            gpsRange = gpsRange,
            emergency = emergency,
        )
        return GenericResponse(
            data = aggregate,
            success = true,
            statusCode = HttpStatus.OK.value(),
        )
    }
}
