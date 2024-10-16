package com.cj.smartworker.business.day_report.port.out

import com.cj.smartworker.business.day_report.dto.response.DayReportResponse

interface SearchByNamePort {
    fun searchByName(name: String): List<DayReportResponse>
}
