package com.he1extg.converterapi.controller

import com.he1extg.converterapi.converter.Converter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v1")
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
            <div>Server datetime: ${LocalDateTime.now()}</div>
            <div>"Converter: PDF to MP3" - API</div>
            <p></p>
            <div>Endpoints:</div>
            <div>1. URI - <a href=$fileApiUriStr>$fileApiUriStr</a></div>
            <div>Request: Method - POST; Content type - multipart/form-data; Body param - file: MultipartFile (must be a pdf file)</div>
            <div>Response: Content type - application/json; Return type - ByteArrayResource (mp3 byte array resource)</div>
            <div>2. URI - <a href=$textApiUriStr>$textApiUriStr</a></div>
            <div>Request: Method - POST; Content type - multipart/form-data; ; Body param - text: ByteArray (any text performed as byte array)</div>
            <div>Response: Content type - application/json; Return type - ByteArray (mp3 byte array)</div>
         """.trimIndent()
    }

    @PostMapping("/file")
    fun convertFile(@RequestParam file: MultipartFile): ResponseEntity<Resource> {
        if (!file.isEmpty) {
            val newFileName = file.originalFilename!!.split(".").first() + ".mp3"
            val converted = converter.convert(file.inputStream)
            val resource : Resource = object : ByteArrayResource(converted.readBytes()) {
                override fun getFilename(): String = newFileName
            }
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "")
                .body(resource)
        }
        return ResponseEntity.noContent()
            .header(HttpHeaders.CONTENT_DISPOSITION, "")
            .build()
    }

    @PostMapping("/text")
    fun convertText(@RequestParam text: ByteArray): ResponseEntity<ByteArray> {
        if (text.isNotEmpty()) {
            val converted = converter.convert(text.decodeToString())
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "")
                .body(converted.readBytes())
        }
        return ResponseEntity.noContent()
            .header(HttpHeaders.CONTENT_DISPOSITION, "")
            .build()
    }
}