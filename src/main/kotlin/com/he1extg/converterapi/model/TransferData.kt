package com.he1extg.converterapi.model

import java.io.Serializable

class TransferData : Serializable {
    var dataa: ByteArray? = null
        set(value) {
            field = value
            length = value?.size ?: 0
        }
    var length = 0
        private set
}