package com.cj.smartworker.application.heart_rate

import com.cj.smartworker.annotation.WebAdapter
import com.cj.smartworker.application.response.GenericResponse
import com.cj.smartworker.business.heart_rate.dto.command.HeartRateCommand
import com.cj.smartworker.business.heart_rate.port.`in`.SaveHeartRateUseCase
import com.cj.smartworker.security.MemberContext.MEMBER
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "심박수 API", description = "심박수 저장 및 조회 API 입니다.")
@WebAdapter
@RestController
@RequestMapping("/api/v1/heart-rate")
internal class SaveHeartRateController(
    private val saveHeartRateUseCase: SaveHeartRateUseCase,
) {

    @Operation(
        summary = "회원 심박수 저장",
        description = "회원 심박수를 저장합니다.",
    )
    @ApiResponse(responseCode = "201", description = "저장 성공")
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    fun saveHeartRate(@RequestBody command: HeartRateCommand): GenericResponse<Unit> {
        saveHeartRateUseCase.save(
            command = command,
            member = MEMBER,
        )

        return GenericResponse(
            data = Unit,
            statusCode = HttpStatus.CREATED.value(),
            success = true,
        )
    }
}
