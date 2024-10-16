package com.cj.smartworker.business.member.service

import com.cj.smartworker.business.member.dto.response.MemberPagingResponse
import com.cj.smartworker.business.member.port.`in`.FindAdminUseCase
import com.cj.smartworker.business.member.port.out.FindAdminPort
import com.cj.smartworker.business.member.util.MaskingUtil
import com.cj.smartworker.domain.member.valueobject.EmployeeName
import com.cj.smartworker.domain.member.valueobject.LoginId
import com.cj.smartworker.domain.member.valueobject.Phone
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
internal class FindAdminService(
    private val findAdminPort: FindAdminPort,
) : FindAdminUseCase {
    override fun findAdmins(): List<MemberPagingResponse> {
        return findAdminPort.findAdminsPagingResponse()
            .map {
                it.apply {
                    phone = MaskingUtil.maskPhone(Phone(it.phone))
                    employeeName = MaskingUtil.maskEmployeeName(EmployeeName(it.employeeName))
                    loginId = MaskingUtil.maskLoginId(LoginId(it.loginId))
                }
            }
    }
}
