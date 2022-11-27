package com.he1extg.converterapi.controller

import com.he1extg.converterapi.model.TransferData
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.io.FileSystemResource
import org.springframework.http.*
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.servlet.function.RequestPredicates.contentType

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class ConverterControllerTest {

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @Test
    fun convertFile() {
        val kkk = FileSystemResource("E:/test.pdf").inputStream.readBytes()
        val zzz = TransferData().apply {
            dataa = kkk
        }
        val requestEntity = RequestEntity.post("/api/v1/file")
            //.contentType(MediaType.ALL)
            //contentType(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(
                /*LinkedMultiValueMap<String, Any>().apply {
                    add("data", zzz.dataa)
                }*/
                zzz.dataa!!
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
                    add("data", "Hello World!")
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