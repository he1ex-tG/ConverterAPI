package com.he1extg.converterapi.converter

import java.io.InputStream

interface PDFReader {
    fun extractText(filePathString: String): String
    fun extractText(file: InputStream): String
}