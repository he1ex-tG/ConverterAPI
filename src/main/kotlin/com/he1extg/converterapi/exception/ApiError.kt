package com.he1extg.converterapi.exception

import org.springframework.http.HttpStatus
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class ApiError(
    val status: HttpStatus,
    val message: String,
    private val ex: Throwable
) {
    val timestamp: String = DateTimeFormatter
        .ofPattern("HH:mm:ss.SSSSSS dd.MM.yyyy")
        .withZone(ZoneOffset.UTC)
        .format(Instant.now())
    val debugMessage = ex.localizedMessage
    val subErrors: List<ApiSubError>? = null
}