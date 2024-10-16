package com.cj.smartworker.application.heart_rate

import com.cj.smartworker.annotation.WebAdapter
import com.cj.smartworker.application.response.GenericResponse
import com.cj.smartworker.business.heart_rate.dto.response.HeartRateAggregateResponse
import com.cj.smartworker.business.heart_rate.port.`in`.AggregateHeartRateUseCase
import com.cj.smartworker.domain.member.valueobject.MemberId
import com.cj.smartworker.security.MemberContext.MEMBER
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@Tag(name = "심박수 API", description = "심박수 저장 및 조회 API 입니다.")
@WebAdapter
@RestController
@RequestMapping("/api/v1/heart-rate")
internal class AggregateHeartRateController(
    private val aggregateHeartRateUseCase: AggregateHeartRateUseCase,
) {

    @Operation(
        summary = "심박수 집계 조회[Admin]",
        description = "심박수 집계 조회 Admin이 다른 직원의 심박수를 조회할 때 사용합니다.",
        parameters = [
            Parameter(name = "start", description = "심박수 집계 조회 시작 시간", required = true, example = "2021-01-01 00:00:00"),
            Parameter(name = "end", description = "심박수 집계 조회 끝나는 시간", required = true, example = "2021-01-01 23:59:59"),
        ]
    )
    @ApiResponse(responseCode = "200", description = "집계 성공")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/aggregate/{memberId}")
    fun aggregateHeartRate(
        @PathVariable("memberId") memberId: Long,
        @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") start: LocalDateTime,
        @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") end: LocalDateTime,
    ): GenericResponse<List<HeartRateAggregateResponse>> {
        val aggregateHeartRate = aggregateHeartRateUseCase.aggregateHeartRate(
            memberId = MemberId(memberId),
            start = start,
            end = end,
        )
        return GenericResponse(
            data = aggregateHeartRate,
            success = true,
            statusCode = HttpStatus.OK.value(),
        )
    }

    @Operation(
        summary = "심박수 집계 조회[Employee]",
        description = "직원이 본인 심박수를 조회합니다.",
        parameters = [
            Parameter(name = "start", description = "심박수 집계 조회 시작 시간", required = true, example = "2021-01-01 00:00:00"),
            Parameter(name = "end", description = "심박수 집계 조회 끝나는 시간", required = true, example = "2021-01-01 23:59:59"),
        ]
    )
    @ApiResponse(responseCode = "200", description = "집계 성공")
    @GetMapping("/aggregate")
    fun aggregateHeartRate(
        @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") start: LocalDateTime,
        @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") end: LocalDateTime,
    ): GenericResponse<List<HeartRateAggregateResponse>> {
        MEMBER
        val aggregateHeartRate = aggregateHeartRateUseCase.aggregateHeartRate(
            memberId = MEMBER.memberId!!,
            start = start,
            end = end,
        )
        return GenericResponse(
            data = aggregateHeartRate,
            success = true,
            statusCode = HttpStatus.OK.value(),
        )
    }
}
