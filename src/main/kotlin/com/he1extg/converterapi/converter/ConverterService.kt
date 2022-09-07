package com.he1extg.converterapi.converter

import com.he1extg.converterapi.converter.pdf.PDFReader
import com.he1extg.converterapi.converter.tts.TTS
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.InputStream

@Service
class ConverterService : Converter {

    @Autowired
    lateinit var pdfReader: PDFReader
    @Autowired
    lateinit var tts: TTS

    override fun convert(inputStream: InputStream): InputStream {
        val text = pdfReader.extractText(inputStream)
        return convert(text)
    }

    override fun convert(text: String): InputStream {
        return tts.stream(text)
    }
}