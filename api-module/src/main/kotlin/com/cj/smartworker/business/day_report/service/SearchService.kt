package com.cj.smartworker.business.day_report.service

import com.cj.smartworker.business.day_report.dto.response.DayReportResponse
import com.cj.smartworker.business.day_report.port.out.SearchByNamePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SearchService(
    private val searchByNamePort: SearchByNamePort,
) {

    @Transactional(readOnly = true)
    fun searchByName(name: String): List<DayReportResponse> {
        if (name.length < 2) {
            throw IllegalArgumentException("검색 시 이름은 두 글자 이상으로 입력해 주세요.")
        }
        return searchByNamePort.searchByName(name)
    }
}
