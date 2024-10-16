package com.cj.smartworker.application.member

import com.cj.smartworker.annotation.WebAdapter
import com.cj.smartworker.application.response.GenericResponse
import com.cj.smartworker.business.member.dto.response.MemberPagingResponse
import com.cj.smartworker.business.member.dto.response.MemberResponse
import com.cj.smartworker.business.member.port.`in`.FindAdminUseCase
import com.cj.smartworker.business.member.port.`in`.FindMemberUseCase
import com.cj.smartworker.domain.member.valueobject.MemberId
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "회원 API", description = "회원 가입, 로그인, 로그아웃, 회원 정보 수정, 회원 탈퇴 등 API 목록입니다.")
@WebAdapter
@RestController
@RequestMapping("/api/v1/member")
internal class FindMemberController(
    private val findMemberUseCase: FindMemberUseCase,
    private val findAdminUseCase: FindAdminUseCase,
) {

    @Operation(
        summary = "회원 id로 회원 상세 조회",
        description = "회원 id로 회원 상세 조회. Admin권한만 가능합니다.",
    )
    @ApiResponse(responseCode = "200", description = "상세 조회 성공")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{memberId}")
    fun findMember(@PathVariable("memberId") memberId: Long): GenericResponse<MemberResponse> {
        val memberResponse = findMemberUseCase.findMember(MemberId(memberId))
        return GenericResponse(
            data = memberResponse,
            statusCode = HttpStatus.OK.value(),
            success = true,
        )
    }


    @Operation(
        summary = "Admin권한 관리자 이름순으로 조회 [Admin]",
        description = "Admin권한 관리자를 이름순으로 조회합니다. [Admin]",
    )
    @ApiResponse(responseCode = "200", description = "Admin권한 관리자 이름순으로 조회")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admins")
    fun findAdmins(): GenericResponse<List<MemberPagingResponse>> {
        val findAdmins = findAdminUseCase.findAdmins()
        return GenericResponse(
            data = findAdmins,
            statusCode = HttpStatus.OK.value(),
            success = true,
        )
    }
}
