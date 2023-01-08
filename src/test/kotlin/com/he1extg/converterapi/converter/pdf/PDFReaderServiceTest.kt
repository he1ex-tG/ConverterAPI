package com.he1extg.converterapi.converter.pdf

import com.itextpdf.text.exceptions.InvalidPdfException
import com.itextpdf.text.pdf.PdfReader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.FileSystemResource

@SpringBootTest
internal class PDFReaderServiceTest {

    @Autowired
    lateinit var pdfReaderService: PDFReader

    @Test
    fun `extractText from correct input data`() {
        val correctPdfFile = FileSystemResource("E:/test.pdf").inputStream

        val answer = pdfReaderService.extractText(correctPdfFile)

        assertThat(answer).isNotEmpty
    }

    @Test
    fun `extractText from incorrect input data - file is not pdf`() {
        val incorrectPdfFile = FileSystemResource("E:/test.mp3").inputStream

        assertThrows(InvalidPdfException::class.java) {
            pdfReaderService.extractText(incorrectPdfFile)
        }
    }

    @Test
    fun `extractText from incorrect input data - not pdf bytearray`() {
        val incorrectPdfFile = byteArrayOf(100, 100, 100, 100, 100, 100, 100).inputStream()

        assertThrows(InvalidPdfException::class.java) {
            pdfReaderService.extractText(incorrectPdfFile)
        }
    }

    @Test
    fun `test PdfReader class - incorrect file`() {
        val incorrectPdfFilePath = "E:/test.mp3"
        assertThrows(InvalidPdfException::class.java) {
            PdfReader(incorrectPdfFilePath)
        }
    }

    @Test
    fun `test PdfReader class - incorrect input data`() {
        val incorrectInputData = "E:/test.mp3".toByteArray().inputStream()
        assertThrows(InvalidPdfException::class.java) {
            PdfReader(incorrectInputData)
        }
    }
}