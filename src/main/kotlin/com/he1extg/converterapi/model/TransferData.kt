package com.he1extg.converterapi.model

import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
interface TransferData {
    val content: ByteArray?
    val contentSize: Int
}