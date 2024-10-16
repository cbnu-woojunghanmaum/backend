package com.cj.smartworker.application.heathcheck

import com.cj.smartworker.annotation.WebAdapter
import com.cj.smartworker.domain.member.entity.Member
import com.cj.smartworker.security.MemberContext.MEMBER
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@WebAdapter
@RestController
@Tag(name = "권한 테스트용 API", description = "권한 테스트용 API입니다. 기능적으로 쓰이지 않습니다.")
@RequestMapping("/api/v1/check")
internal class HealthCheckController {

    @Operation(summary = "직원 권한 테스트")
    @GetMapping("/employee")
    fun checkEmployee(): Member {
        val member = MEMBER
        return member
    }

    @Operation(summary = "관리자 권한 테스트")
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    fun checkAdmin(): Member {
        val member = MEMBER
        return member
    }
}
