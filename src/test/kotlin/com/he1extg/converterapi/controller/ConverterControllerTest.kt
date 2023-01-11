package com.he1extg.converterapi.controller

import com.he1extg.converterapi.exception.MAIN_PREFIX
import com.he1extg.converterapi.model.TransferData
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.io.FileSystemResource
import org.springframework.http.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class ConverterControllerTest {

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    /**
     * File conversion tests
     */

    @Test
    fun `convertFile normal data`() {
        val transferData = TransferData(FileSystemResource("E:/test.pdf").inputStream.readBytes())
        val requestEntity = RequestEntity.post("/api/v1/file")
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                transferData
            )

        val answer = testRestTemplate.exchange(requestEntity, ByteArray::class.java)

        assertThat(answer.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(answer.body).isNotNull
        answer.body?.let {
            assertThat(it.decodeToString()).contains("LAME")
        }
    }

    @Test
    fun `convertFile input not pdf file`() {
        val transferData = TransferData(FileSystemResource("E:/test.mp3").inputStream.readBytes())
        val requestEntity = RequestEntity.post("/api/v1/file")
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                transferData
            )

        val answer = testRestTemplate.exchange(requestEntity, ByteArray::class.java)

        assertThat(answer.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(answer.body).isNotNull
        answer.body?.let {
            assertThat(it.decodeToString()).contains(MAIN_PREFIX)
        }
    }

    @Test
    fun `convertFile empty file`() {
        val transferData = TransferData(byteArrayOf())
        val requestEntity = RequestEntity.post("/api/v1/file")
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                transferData
            )

        val answer = testRestTemplate.exchange(requestEntity, ByteArray::class.java)

        assertThat(answer.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(answer.body).isNotNull
        answer.body?.let {
            assertThat(it.decodeToString()).contains(MAIN_PREFIX)
        }
    }

    @Test
    fun `convertFile blank pdf file`() {
        val transferData = TransferData(FileSystemResource("E:/blank.pdf").inputStream.readBytes())
        val requestEntity = RequestEntity.post("/api/v1/file")
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                transferData
            )

        val answer = testRestTemplate.exchange(requestEntity, ByteArray::class.java)

        assertThat(answer.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(answer.body).isNotNull
        answer.body?.let {
            assertThat(it.decodeToString()).contains("An empty string was passed to the TTS module.")
        }
    }

    /**
     * Text conversion tests
     */

    @Test
    fun `convertText normal data`() {
        val transferData = TransferData("Hello world!".toByteArray())
        val requestEntity = RequestEntity.post("/api/v1/text")
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                transferData
            )

        val answer = testRestTemplate.exchange(requestEntity, ByteArray::class.java)

        assertThat(answer.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(answer.body).isNotNull
        answer.body?.let {
            assertThat(it.decodeToString()).contains("LAME")
        }
    }

    @Test
    fun `convertText empty text`() {
        val transferData = TransferData(byteArrayOf())
        val requestEntity = RequestEntity.post("/api/v1/text")
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                transferData
            )

        val answer = testRestTemplate.exchange(requestEntity, ByteArray::class.java)

        assertThat(answer.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(answer.body).isNotNull
        answer.body?.let {
            assertThat(it.decodeToString()).contains(MAIN_PREFIX)
        }
    }

}