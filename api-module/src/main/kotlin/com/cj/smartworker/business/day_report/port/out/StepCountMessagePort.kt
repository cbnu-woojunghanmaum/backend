package com.cj.smartworker.business.day_report.port.out

import com.cj.smartworker.kafka.model.StepCountRequest

interface StepCountMessagePort {
    fun sendStepPublish(stepCount: StepCountRequest)
}
