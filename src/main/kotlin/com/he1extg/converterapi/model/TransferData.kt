package com.he1extg.converterapi.model

class TransferData(
    val content: ByteArray
) {
    val contentSize: Int
        get() = content.size
}