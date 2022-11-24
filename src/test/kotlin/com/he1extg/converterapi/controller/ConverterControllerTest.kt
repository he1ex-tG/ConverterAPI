package com.he1extg.converterapi.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity
import org.springframework.util.LinkedMultiValueMap

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class ConverterControllerTest {

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @Test
    fun convertFile() {
        val requestEntity = RequestEntity.post("/api/v1/file")
            //.contentType(MediaType.MULTIPART_FORM_DATA)
            .body(
                LinkedMultiValueMap<String, Any>().apply {
                    add("file", FileSystemResource("E:/test.pdf").inputStream.readAllBytes())
                }
            )

        val answer = testRestTemplate.exchange(requestEntity, ByteArray::class.java)

        assertThat(answer.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(answer.body).isNotNull
        answer.body?.let {
            assertThat(it.decodeToString()).contains("LAME")
        }
    }

    @Test
    fun convertText() {
        val requestEntity = RequestEntity.post("/api/v1/text")
            .body(
                LinkedMultiValueMap<String, Any>().apply {
                    add("text", "Hello World!")
                }
            )

        val answer = testRestTemplate.exchange(requestEntity, ByteArray::class.java)

        assertThat(answer.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(answer.body).isNotNull
        answer.body?.let {
            assertThat(it.decodeToString()).contains("LAME")
        }
    }
}