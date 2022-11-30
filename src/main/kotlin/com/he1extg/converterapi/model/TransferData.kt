package com.he1extg.converterapi.model

class TransferData {
    var content: ByteArray? = null
        set(value) {
            field = value
            length = value?.size ?: 0
        }
    var length = 0
        private set
}