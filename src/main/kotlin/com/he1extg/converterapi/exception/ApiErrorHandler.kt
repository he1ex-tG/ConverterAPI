package com.he1extg.converterapi.exception

import com.itextpdf.text.exceptions.BadPasswordException
import com.itextpdf.text.exceptions.IllegalPdfSyntaxException
import com.itextpdf.text.exceptions.InvalidPdfException
import com.itextpdf.text.exceptions.UnsupportedPdfException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ApiErrorHandler : ResponseEntityExceptionHandler() {

    /**
     * ResponseEntity builder
     */
    private fun buildResponseEntity(apiError: ApiError): ResponseEntity<ApiError> {
        return ResponseEntity(apiError, apiError.status)
    }

    /**
     * Default exception handlers
     */

    /**
     * Custom exception handlers
     */
    @ExceptionHandler(TtsEmptyStringException::class)
    fun handlerConverterEmptyStringException(ex: TtsEmptyStringException): ResponseEntity<ApiError> {
        val apiError = ApiError(HttpStatus.BAD_REQUEST, ex.message, ex)
        return buildResponseEntity(apiError)
    }

    /**
     * ITextPDF module exception handlers
     */
    @ExceptionHandler(BadPasswordException::class)
    fun handlerBadPasswordException(ex: BadPasswordException): ResponseEntity<ApiError> {
        val apiError = ApiError(HttpStatus.BAD_REQUEST, ex.message ?: "PDF file exception.", ex)
        return buildResponseEntity(apiError)
    }

    @ExceptionHandler(IllegalPdfSyntaxException::class)
    fun handlerIllegalPdfSyntaxException(ex: IllegalPdfSyntaxException): ResponseEntity<ApiError> {
        val apiError = ApiError(HttpStatus.BAD_REQUEST, ex.message ?: "PDF file exception.", ex)
        return buildResponseEntity(apiError)
    }

    @ExceptionHandler(InvalidPdfException::class)
    fun handlerInvalidPdfException(ex: InvalidPdfException): ResponseEntity<ApiError> {
        val apiError = ApiError(HttpStatus.BAD_REQUEST, ex.message ?: "PDF file exception.", ex)
        return buildResponseEntity(apiError)
    }

    @ExceptionHandler(UnsupportedPdfException::class)
    fun handlerUnsupportedPdfException(ex: UnsupportedPdfException): ResponseEntity<ApiError> {
        val apiError = ApiError(HttpStatus.BAD_REQUEST, ex.message ?: "PDF file exception.", ex)
        return buildResponseEntity(apiError)
    }
}