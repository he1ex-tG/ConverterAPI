package com.he1extg.converterapi.controller

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

    @Test
    fun `convertFile normal data`() {
        val transferData = TransferData().apply {
            content = FileSystemResource("E:/test.pdf").inputStream.readBytes()
        }
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
    fun `convertFile null file`() {
        val transferData = TransferData().apply {
            content = null
        }
        val requestEntity = RequestEntity.post("/api/v1/file")
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                transferData
            )

        val answer = testRestTemplate.exchange(requestEntity, ByteArray::class.java)

        assertThat(answer.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun `convertFile empty file`() {
        val transferData = TransferData().apply {
            content = byteArrayOf()
        }
        val requestEntity = RequestEntity.post("/api/v1/file")
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                transferData
            )

        val answer = testRestTemplate.exchange(requestEntity, ByteArray::class.java)

        assertThat(answer.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
    }

    @Test
    fun `convertFile file size incorrect`() {
        class TestTransferData {
            var content: ByteArray? = null
            var length: Int = 0
        }

        val transferData = TestTransferData().apply {
            content = FileSystemResource("E:/test.pdf").inputStream.readBytes()
            length = 100
        }
        val requestEntity = RequestEntity.post("/api/v1/file")
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                transferData
            )

        val answer = testRestTemplate.exchange(requestEntity, ByteArray::class.java)

        assertThat(answer.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun `convertText normal data`() {
        val transferData = TransferData().apply {
            content = "Hello world!".toByteArray()
        }
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
    fun `convertText null text`() {
        val transferData = TransferData().apply {
            content = null
        }
        val requestEntity = RequestEntity.post("/api/v1/text")
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                transferData
            )

        val answer = testRestTemplate.exchange(requestEntity, ByteArray::class.java)

        assertThat(answer.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun `convertText empty text`() {
        val transferData = TransferData().apply {
            content = byteArrayOf()
        }
        val requestEntity = RequestEntity.post("/api/v1/text")
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                transferData
            )

        val answer = testRestTemplate.exchange(requestEntity, ByteArray::class.java)

        assertThat(answer.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
    }

    @Test
    fun `convertText text size incorrect`() {
        class TestTransferData {
            var content: ByteArray? = null
            var length: Int = 0
        }

        val transferData = TestTransferData().apply {
            content = "Hello world!".toByteArray()
            length = 1
        }
        val requestEntity = RequestEntity.post("/api/v1/text")
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                transferData
            )

        val answer = testRestTemplate.exchange(requestEntity, ByteArray::class.java)

        assertThat(answer.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }
}