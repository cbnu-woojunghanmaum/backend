package com.cj.smartworker.domain.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

const val KOREA_ZONE = "Asia/Seoul"

val KST: ZoneId = ZoneId.of(KOREA_ZONE)

fun Instant.toKstLocalDateTime(): LocalDateTime = LocalDateTime.ofInstant(this, KST)

inline fun <reified T> T.logger(): Logger = LoggerFactory.getLogger(T::class.java)!!
