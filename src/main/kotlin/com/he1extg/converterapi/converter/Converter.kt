package com.he1extg.converterapi.converter

import java.io.InputStream

interface Converter {

    fun convert(inputStream: InputStream): InputStream
    fun convert(text: String): InputStream
}