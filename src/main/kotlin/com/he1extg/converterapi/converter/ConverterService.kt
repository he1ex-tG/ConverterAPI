package com.he1extg.converterapi.converter

import java.io.InputStream

interface ConverterService {

    fun convert(inputStream: InputStream): InputStream
    fun convert(text: String): InputStream
}