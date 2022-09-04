package com.he1extg.converterapi.converter

import java.io.InputStream

interface TTS {

    fun speak(text: String)
    fun speak(inputStream: InputStream)
    fun stream(text: String): InputStream
}