package com.he1extg.converterapi.dto

class FileConvertDTO(
    val content: ByteArray
) {
    val contentSize: Int
        get() = content.size
}