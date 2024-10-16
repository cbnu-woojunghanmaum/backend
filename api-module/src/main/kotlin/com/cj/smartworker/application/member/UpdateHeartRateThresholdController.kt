package com.cj.smartworker.application.member

import com.cj.smartworker.annotation.WebAdapter
import com.cj.smartworker.application.response.GenericResponse
import com.cj.smartworker.business.heart_rate.dto.command.HeartRateCommand
import com.cj.smartworker.business.member.port.`in`.UpdateHeartRateThresholdUseCase
import com.cj.smartworker.domain.member.valueobject.MemberId
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@Tag(name = "회원 API", description = "회원 가입, 로그인, 로그아웃, 회원 정보 수정, 회원 탈퇴 등 API 목록입니다.")
@WebAdapter
@RestController
@RequestMapping("/api/v1/member")
internal class UpdateHeartRateThresholdController(
    private val updateHeartRateThresholdUseCase: UpdateHeartRateThresholdUseCase,
) {

    @Operation(
        summary = "회원 심박수 임계치 수정",
        description = "회원 심박수 임계치를 수정합니다. Admin권한에서 사용 가능합니다.",
    )
    @ApiResponse(responseCode = "200", description = "수정 성공")
    @PutMapping("/heart-rate-threshold")
    @PreAuthorize("hasRole('ADMIN')")
    fun updateHeartRateThreshold(
        @RequestParam("threshold") threshold: Int,
        @RequestParam("memberId") memberId: Long,
    ): GenericResponse<Unit> {
        updateHeartRateThresholdUseCase.update(
            command = HeartRateCommand(heartRate = threshold),
            memberId = MemberId(memberId),
        )

        return GenericResponse(
            data = Unit,
            success = true,
            statusCode = HttpStatus.OK.value(),
        )
    }
}
