package com.he1extg.converterapi.controller

import com.he1extg.converterapi.converter.Converter
import com.he1extg.converterapi.dto.FileConvertDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v1")
class ConverterController {

    @Autowired
    lateinit var converter: Converter

    /**
     * TODO Create root info builder based on annotations and reflection
     */
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

    @PostMapping("/file")
    fun convertFile(@RequestBody fileConvertDTO: FileConvertDTO): ResponseEntity<FileConvertDTO> {
        val conversionResult = converter.convert(fileConvertDTO.content.inputStream())
        return ResponseEntity
            .ok()
            .body(FileConvertDTO(conversionResult.readBytes()))
    }

    @PostMapping("/text")
    fun convertText(@RequestBody fileConvertDTO: FileConvertDTO): ResponseEntity<FileConvertDTO> {
        val conversionResult = converter.convert(fileConvertDTO.content.decodeToString())
        return ResponseEntity
            .ok()
            .body(FileConvertDTO(conversionResult.readBytes()))
    }
}