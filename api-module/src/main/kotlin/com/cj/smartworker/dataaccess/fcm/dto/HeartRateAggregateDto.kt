package com.cj.smartworker.dataaccess.fcm.dto

import com.querydsl.core.annotations.QueryProjection

data class HeartRateAggregateDto @QueryProjection constructor(
    val x: Double,
    val y: Double,
    val count: Long,
)
