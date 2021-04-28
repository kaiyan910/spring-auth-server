package com.qbssystem.auth.exception

import com.qbssystem.auth.createGeneralizedJson
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.time.LocalDate
import java.time.LocalDateTime

class GeneralizedException(
    val status: HttpStatus,
    val prefix: String,
    val code: String,
    message: String,
    val details: String,
    val traceId: String?
) : Exception(message) {

    fun asResponseBody(): ResponseEntity<Any> {

        return ResponseEntity
            .status(status)
            .body(
                createGeneralizedJson(
                    code = "$prefix-$code",
                    message = message!!,
                    details = details,
                    traceId = traceId
                )
            )
    }
}
