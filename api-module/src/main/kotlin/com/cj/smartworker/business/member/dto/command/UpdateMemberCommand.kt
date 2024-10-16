package com.cj.smartworker.business.member.dto.command

import com.cj.smartworker.domain.member.valueobject.*
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "회원 정보 수정 명령")
data class UpdateMemberCommand(
    @Schema(description = "회원 ID", example = "3")
    val memberId: MemberId,
    @Schema(description = "전화번호")
    val phone: Phone,
    @Schema(description = "성별")
    val gender: Gender,
    @Schema(description = "권한")
    val authority: Authority,
    @Schema(description = "년도")
    val year: Year,
    @Schema(description = "월")
    val month: Month,
    @Schema(description = "일")
    val day: Day,
    @Schema(description = "이름")
    val employeeName: EmployeeName,
    @Schema(description = "이메일")
    val email: Email?,
    @Schema(description = "심박수 임계치")
    val heartRateThreshold: Int,
)
