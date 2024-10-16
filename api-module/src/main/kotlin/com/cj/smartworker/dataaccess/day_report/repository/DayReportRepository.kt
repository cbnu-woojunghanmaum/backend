package com.cj.smartworker.dataaccess.day_report.repository

import com.cj.smartworker.dataaccess.day_report.entity.DayReportJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DayReportRepository: JpaRepository<DayReportJpaEntity, Long> {
}
