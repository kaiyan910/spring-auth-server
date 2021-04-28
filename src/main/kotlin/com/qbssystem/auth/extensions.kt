package com.qbssystem.auth

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


fun createGeneralizedJson(
    code: String,
    message: String,
    details: String,
    traceId: String?
): Map<String, Any> {

    return mapOf(
        "code" to code,
        "message" to message,
        "details" to details,
        "date" to LocalDateTime.now()
    )
}

fun LocalDateTime.asPattern(pattern: String = Constant.DATETIME_FORMAT): String =
    format(DateTimeFormatter.ofPattern(pattern))