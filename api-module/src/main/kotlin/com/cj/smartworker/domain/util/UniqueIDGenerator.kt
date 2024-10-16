package com.cj.smartworker.domain.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun generateUniqueKey(createdAt: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")
    val datePart = createdAt.format(formatter)
    val randomPart = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
    return "$datePart$randomPart"
}
