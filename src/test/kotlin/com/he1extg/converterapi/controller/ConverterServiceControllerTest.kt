package com.he1extg.converterapi.controller

import com.he1extg.converterapi.dto.FileConvertDTO
import com.he1extg.converterapi.exception.ApiError
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.io.FileSystemResource
import org.springframework.http.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class ConverterServiceControllerTest {

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    /**
     * File conversion tests
     */

    @Test
    fun `convertFile normal TransferData content - result ok`() {
        val fileConvertDTO = FileConvertDTO(FileSystemResource("E:/test.pdf").inputStream.readBytes())
        val requestEntity = RequestEntity.post("/api/v1/file")
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                fileConvertDTO
            )

        val answer = testRestTemplate.exchange(requestEntity, FileConvertDTO::class.java)

        assertThat(answer.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(answer.body).isNotNull
        answer.body?.let {
            assertThat(it.content.decodeToString()).contains("LAME")
        }
    }

    @Test
    fun `convertFile TransferData content is not pdf file - result bad_request`() {
        val fileConvertDTO = FileConvertDTO(FileSystemResource("E:/test.mp3").inputStream.readBytes())
        val requestEntity = RequestEntity.post("/api/v1/file")
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                fileConvertDTO
            )

        val answer = testRestTemplate.exchange(requestEntity, ApiError::class.java)

        assertThat(answer.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(answer.body).isNotNull
        answer.body?.let {
            assertThat(it.message).contains("Converter")
            assertThat(it.debugMessage).isEqualTo("PDF header signature not found.")
        }
    }

    @Test
    fun `convertFile empty TransferData content - result bad_request`() {
        val fileConvertDTO = FileConvertDTO(byteArrayOf())
        val requestEntity = RequestEntity.post("/api/v1/file")
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                fileConvertDTO
            )

        val answer = testRestTemplate.exchange(requestEntity, ApiError::class.java)

        assertThat(answer.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(answer.body).isNotNull
        answer.body?.let {
            assertThat(it.message).contains("Converter")
            assertThat(it.debugMessage).isEqualTo("PDF header signature not found.")
        }
    }

    @Test
    fun `convertFile normal TransferData content and blank pdf file - result bad_request`() {
        val fileConvertDTO = FileConvertDTO(FileSystemResource("E:/blank.pdf").inputStream.readBytes())
        val requestEntity = RequestEntity.post("/api/v1/file")
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                fileConvertDTO
            )

        val answer = testRestTemplate.exchange(requestEntity, ApiError::class.java)

        assertThat(answer.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(answer.body).isNotNull
        answer.body?.let {
            assertThat(it.message).contains("Converter")
            assertThat(it.debugMessage).isEqualTo("An empty string was passed to the TTS module.")
        }
    }

    /**
     * Text conversion tests
     */

    @Test
    fun `convertText normal TransferData content - result ok`() {
        val fileConvertDTO = FileConvertDTO("Hello world!".toByteArray())
        val requestEntity = RequestEntity.post("/api/v1/text")
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                fileConvertDTO
            )

        val answer = testRestTemplate.exchange(requestEntity, FileConvertDTO::class.java)

        assertThat(answer.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(answer.body).isNotNull
        answer.body?.let {
            assertThat(it.content.decodeToString()).contains("LAME")
        }
    }

    @Test
    fun `convertText empty TransferData content - result bad_request`() {
        val fileConvertDTO = FileConvertDTO(byteArrayOf())
        val requestEntity = RequestEntity.post("/api/v1/text")
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                fileConvertDTO
            )

        val answer = testRestTemplate.exchange(requestEntity, ApiError::class.java)

        assertThat(answer.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(answer.body).isNotNull
        answer.body?.let {
            assertThat(it.message).contains("Converter")
            assertThat(it.debugMessage).isEqualTo("An empty string was passed to the TTS module.")
        }
    }
}