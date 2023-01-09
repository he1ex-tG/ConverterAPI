package com.he1extg.converterapi.exception

import org.springframework.http.HttpStatus
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class ApiError private constructor() {

    private var status: HttpStatus? = null
    private val timestamp: String = DateTimeFormatter
        .ofPattern("HH:mm:ss.SSSSSS dd.MM.yyyy")
        .withZone(ZoneOffset.UTC)
        .format(Instant.now())
    private var message: String? = null
    private var debugMessage: String? = null
    private val subErrors: List<ApiSubError>? = null

    constructor(status: HttpStatus?) : this() {
        this.status = status
    }

    constructor(status: HttpStatus?, ex: Throwable) : this() {
        this.status = status
        message = "Unexpected error"
        debugMessage = ex.localizedMessage
    }

    constructor(status: HttpStatus?, message: String?, ex: Throwable) : this() {
        this.status = status
        this.message = message
        debugMessage = ex.localizedMessage
    }
}