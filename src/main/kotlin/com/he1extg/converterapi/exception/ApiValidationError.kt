package com.he1extg.converterapi.exception

class ApiValidationError(
    val `object`: String,
    val message: String,
    val field: String,
    val rejectedValue: Any
)