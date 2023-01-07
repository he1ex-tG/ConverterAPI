package com.he1extg.converterapi.controller

import com.he1extg.converterapi.converter.Converter
import com.he1extg.converterapi.model.TransferData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.Errors
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder
import java.time.LocalDateTime
import javax.validation.Valid
import javax.validation.constraints.NotNull

@RestController
@RequestMapping("/api/v1")
@Validated
class ConverterController {

    @Autowired
    lateinit var converter: Converter

    @GetMapping
    fun root(): String {
        val fileApiUri = MvcUriComponentsBuilder.fromMethodName(
            this::class.java,
            this::convertFile.name,
            ""
        ).build()
        val fileApiUriStr = fileApiUri.scheme + "://" + fileApiUri.host + ":" + fileApiUri.port + fileApiUri.path
        val textApiUri = MvcUriComponentsBuilder.fromMethodName(
            this::class.java,
            this::convertText.name,
            ""
        ).build()
        val textApiUriStr = textApiUri.scheme + "://" + textApiUri.host + ":" + textApiUri.port + textApiUri.path
        return """
            <p>Server datetime: ${LocalDateTime.now()}</p>
            <p>"Converter: PDF to MP3" - API</p>
            <p></p>
            <div>Endpoints:</div>
            <div>1. URI - <a href=$fileApiUriStr>$fileApiUriStr</a></div>
            <div>Request: Method - POST; Content type - application/json; Body - TransferData object with pdf (only!) file</div>
            <div>Response: Content type - application/json; Return type - ByteArray (mp3 byte array)</div>
            <div>2. URI - <a href=$textApiUriStr>$textApiUriStr</a></div>
            <div>Request: Method - POST; Content type - application/json; Body - TransferData object with any text performed as byte array</div>
            <div>Response: Content type - application/json; Return type - ByteArray (mp3 byte array)</div>
         """.trimIndent()
    }

    /**
     * TODO Add validation
     * TODO After that add exception handling (https://stackoverflow.com/questions/32441919/how-return-error-message-in-spring-mvc-controller)
     */
    @PostMapping("/file")
    fun convertFile(
        @Valid
        @NotNull(message = "Transfer Data object must not be null")
        @RequestBody
        transferData: TransferData?,
        errors: Errors
    ): ResponseEntity<Any> {
        if (errors.hasErrors()) {
            errors.allErrors.forEach {
                println(it.toString())
            }
        }
        return try {
            val conversionResult = converter.convert(transferData!!.content!!.inputStream()).readBytes()
            ResponseEntity
                .ok()
                .body(conversionResult)
        } catch (e: Exception) {
            ResponseEntity
                .badRequest()
                .body("ConverterAPI: Bad request!")
        }
    }

    @PostMapping("/text")
    fun convertText(@RequestBody transferData: TransferData?): ResponseEntity<Any> {
        return try {
            val conversionResult = converter.convert(transferData!!.content!!.decodeToString()).readBytes()
            ResponseEntity
                .ok()
                .body(conversionResult)
        } catch (e: Exception) {
            ResponseEntity
                .badRequest()
                .body("ConverterAPI: Bad request!")
        }
    }
}