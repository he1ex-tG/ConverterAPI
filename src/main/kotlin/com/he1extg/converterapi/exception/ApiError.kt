package com.he1extg.converterapi.exception

import org.springframework.http.HttpStatus
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class ApiError(
    val status: HttpStatus,
    val message: String = "${MAIN_PREFIX}: Unexpected error",
    val debugMessage: String = "${MAIN_PREFIX}: Empty debug message",
) {
    val timestamp: String = DateTimeFormatter
        .ofPattern("HH:mm:ss.SSSSSS dd.MM.yyyy")
        .withZone(ZoneOffset.UTC)
        .format(Instant.now())
    val subErrors: List<ApiSubError>? = null

    constructor(status: HttpStatus, ex: Throwable) : this(
        status = status,
        debugMessage = ex.localizedMessage
    )

    constructor(status: HttpStatus, message: String, ex: Throwable) : this(
        status = status,
        message = message,
        debugMessage = ex.localizedMessage
    )
}