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
    fun `convertFile null file`() {
        class MockTransferData(
            val content: ByteArray?
        )
        val mockTransferData = MockTransferData(null)
        val requestEntity = RequestEntity.post("/api/v1/file")
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                mockTransferData
            )

        val answer = testRestTemplate.exchange(requestEntity, ByteArray::class.java)
        println(answer.body?.decodeToString())
        assertThat(answer.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun `convertFile null transferData`() {
        val requestEntity = RequestEntity.post("/api/v1/file")
            .contentType(MediaType.APPLICATION_JSON)
            .build()

        val answer = testRestTemplate.exchange(requestEntity, ByteArray::class.java)

        assertThat(answer.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
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
    }

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
    fun `convertText null text`() {
        class MockTransferData(
            val content: ByteArray?
        )
        val mockTransferData = MockTransferData(null)
        val requestEntity = RequestEntity.post("/api/v1/text")
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                mockTransferData
            )

        val answer = testRestTemplate.exchange(requestEntity, ByteArray::class.java)

        assertThat(answer.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun `convertText null transferData`() {
        val requestEntity = RequestEntity.post("/api/v1/text")
            .contentType(MediaType.APPLICATION_JSON)
            .build()

        val answer = testRestTemplate.exchange(requestEntity, ByteArray::class.java)

        assertThat(answer.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
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
    }

}