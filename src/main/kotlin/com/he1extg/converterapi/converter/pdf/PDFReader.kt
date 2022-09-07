package com.he1extg.converterapi.converter.pdf

import java.io.InputStream

interface PDFReader {
    fun extractText(filePathString: String): String
    fun extractText(file: InputStream): String
}