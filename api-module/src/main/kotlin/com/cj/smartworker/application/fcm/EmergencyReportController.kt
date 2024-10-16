package com.cj.smartworker.application.fcm

import com.cj.smartworker.annotation.WebAdapter
import com.cj.smartworker.application.response.GenericResponse
import com.cj.smartworker.business.fcm.dto.response.EmergencyReportResponse
import com.cj.smartworker.business.fcm.port.`in`.FindEmergencyReportUseCase
import com.cj.smartworker.domain.fcm.valueobject.Emergency
import com.cj.smartworker.domain.member.valueobject.LoginId
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
import java.time.LocalDate

@Tag(name = "신고이력", description = "신고이력 조회 API 목록입니다.")
@WebAdapter
@RestController
@RequestMapping("/api/v1/fcm")
internal class EmergencyReportController(
    private val emergencyReportUseCase: FindEmergencyReportUseCase,
) {

    @Operation(
        summary = "신고 이력 조회 [Admin]",
        description = "신고 이력을 조회합니다. [Admin]",
        parameters = [
            Parameter(name = "start", description = "신고 이력 조회 시작 시간", required = true, example = "2021-01-01"),
            Parameter(name = "end", description = "신고 이력 조회 끝나는 시간", required = true, example = "2021-01-01"),
        ]
    )
    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponse(responseCode = "200", description = "신고 목록 반환")
    @GetMapping("/admin/emergency-report")
    fun emergencyReport(
        @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") start: LocalDate,
        @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") end: LocalDate,
    ): GenericResponse<List<EmergencyReportResponse>> {
        val report = emergencyReportUseCase.findReport(
            start = start.atStartOfDay(),
            end = end.atTime(23, 59, 59),
        )
        return GenericResponse(
            data = report,
            success = true,
            statusCode = HttpStatus.OK.value(),
        )
    }

    @Operation(
        summary = "신고 타입별 신고 이력 조회 [Admin]",
        description = "신고 타입별 신고 이력을 조회합니다. [Admin]",
        parameters = [
            Parameter(name = "start", description = "신고 이력 조회 시작 시간", required = true, example = "2021-01-01"),
            Parameter(name = "end", description = "신고 이력 조회 끝나는 시간", required = true, example = "2021-01-01"),
            Parameter(name = "emergency", description = "심박수 혹은 긴급 신고 type 지정", required = true, example = "HEART_RATE or REPORT"),
        ]
    )
    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponse(responseCode = "200", description = "신고 목록 반환")
    @GetMapping("/admin/emergency-report-by-type")
    fun emergencyReport(
        @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") start: LocalDate,
        @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") end: LocalDate,
        @RequestParam("emergency") emergency: Emergency,
    ): GenericResponse<List<EmergencyReportResponse>> {
        val report = emergencyReportUseCase.findReport(
            start = start.atStartOfDay(),
            end = end.atTime(23, 59, 59),
            emergency = emergency,
        )
        return GenericResponse(
            data = report,
            success = true,
            statusCode = HttpStatus.OK.value(),
        )
    }

    @Operation(
        summary = "신고 이력 조회 [Employee]",
        description = "신고 이력을 조회합니다. [Employee]",
        parameters = [
            Parameter(name = "start", description = "신고 이력 조회 시작 시간", required = true, example = "2021-01-01"),
            Parameter(name = "end", description = "신고 이력 조회 끝나는 시간", required = true, example = "2021-01-01"),
        ]
    )
    @ApiResponse(responseCode = "200", description = "신고 목록 반환")
    @GetMapping("/employee/emergency-report")
    fun myEmergencyReport(
        @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") start: LocalDate,
        @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") end: LocalDate,
    ): GenericResponse<List<EmergencyReportResponse>> {

        return GenericResponse(
            data = emergencyReportUseCase.findReport(
                member = MEMBER,
                start = start.atStartOfDay(),
                end = end.atTime(23, 59, 59),
            ),
            success = true,
            statusCode = HttpStatus.OK.value(),
        )
    }

    @Operation(
        summary = "특정 회원 신고 이력 조회 [Admin]",
        description = "특정 회원 신고 이력을 조회합니다. [Admin]",
        parameters = [
            Parameter(name = "start", description = "신고 이력 조회 시작 시간", required = true, example = "2021-01-01"),
            Parameter(name = "end", description = "신고 이력 조회 끝나는 시간", required = true, example = "2021-01-01"),
        ]
    )
    @ApiResponse(responseCode = "200", description = "신고 목록 반환")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/emergency-report/{memberId}")
    fun emergencyReport(
        @PathVariable("memberId") memberId: Long,
        @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") start: LocalDate,
        @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") end: LocalDate,
    ): GenericResponse<List<EmergencyReportResponse>> {

        return GenericResponse(
            data = emergencyReportUseCase.findReport(
                memberId = MemberId(memberId),
                start = start.atStartOfDay(),
                end = end.atTime(23, 59, 59),
            ),
            success = true,
            statusCode = HttpStatus.OK.value(),
        )
    }

    @Operation(
        summary = "loginId로 신고 이력 조회 [Admin]",
        description = "loginId로 신고 이력을 조회합니다. [Admin]",
        parameters = [
            Parameter(name = "loginId", description = "검색할 로그인 아이디", required = true, example = "qwert1234"),
        ]
    )
    @ApiResponse(responseCode = "200", description = "신고 목록 반환")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/emergency-report/search")
    fun emergencyReport(
        @RequestParam("loginId") loginId: String,
    ): GenericResponse<List<EmergencyReportResponse>> {

        return GenericResponse(
            data = emergencyReportUseCase.findReport(
                loginId1 = LoginId(loginId)
            ),
            success = true,
            statusCode = HttpStatus.OK.value(),
        )
    }

    @Operation(
        summary = "loginId + 날짜로 신고 이력 조회 [Admin]",
        description = "loginId + 날짜로 신고 이력을 조회합니다. [Admin]",
        parameters = [
            Parameter(name = "loginId", description = "검색할 로그인 아이디", required = true, example = "qwert1234"),
            Parameter(name = "start", description = "신고 이력 조회 시작 시간", required = true, example = "2021-01-01"),
            Parameter(name = "end", description = "신고 이력 조회 끝나는 시간", required = true, example = "2021-01-01"),
        ]
    )
    @ApiResponse(responseCode = "200", description = "신고 목록 반환")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/emergency-report/search-with-date")
    fun emergencyReport(
        @RequestParam("loginId") loginId: String,
        @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") start: LocalDate,
        @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") end: LocalDate,
    ): GenericResponse<List<EmergencyReportResponse>> {

        return GenericResponse(
            data = emergencyReportUseCase.findReport(
                loginId1 = LoginId(loginId)
            ),
            success = true,
            statusCode = HttpStatus.OK.value(),
        )
    }
}

