package com.cj.smartworker.application.fcm

import com.cj.smartworker.annotation.WebAdapter
import com.cj.smartworker.application.response.GenericResponse
import com.cj.smartworker.business.fcm.port.`in`.EmergencyAlarmPushUseCase
import com.cj.smartworker.domain.fcm.valueobject.Emergency
import com.cj.smartworker.domain.member.valueobject.EmployeeName
import com.cj.smartworker.security.MemberContext.MEMBER
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "알림 기능 API", description = "신고 알림을 보내는 API 입니다.")
@WebAdapter
@RestController
@RequestMapping("/api/v1/fcm")
internal class FcmPushController(
    private val emergencyAlarmPushUseCase: EmergencyAlarmPushUseCase,
) {

    @Operation(
        summary = "긴급 알람 푸시",
        description = "긴급 알람 푸시를 전송합니다. 전송되는 알림에서 알리는 나이는 만 나이 입니다.",
        parameters = [
            Parameter(name = "x", description = "x 좌표", required = true),
            Parameter(name = "y", description = "y 좌표", required = true),
            Parameter(name = "emergency", description = "HEART_RATE or REPORT", required = true),
        ]
    )
    @ApiResponse(responseCode = "200", description = "푸시 성공. 관리자 이름 목록 반환.")
    @PostMapping("/emergency-alarm")
    fun emergencyAlarmPush(
        @RequestParam("x") x: Float,
        @RequestParam("y") y: Float,
        @RequestParam("emergency") emergency: Emergency,
    ): GenericResponse<List<EmployeeName>> {
        val adminNames = emergencyAlarmPushUseCase.pushEmergencyAlarm(
            member = MEMBER,
            x = x,
            y = y,
            emergency = emergency,
        )

        return GenericResponse(
            data = adminNames,
            success = true,
            statusCode = HttpStatus.OK.value(),
        )
    }
}
