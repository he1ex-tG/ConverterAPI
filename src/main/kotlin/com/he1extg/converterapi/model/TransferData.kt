package com.he1extg.converterapi.model

import javax.validation.Valid
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class TransferData(
    @field:NotNull(message = "Transfer Data object content must not be null")
    @field:NotEmpty(message = "Transfer Data object content must not be empty")
    val content: @Valid ByteArray?
) {
    val contentSize: Int
        get() = content?.size ?: 0
}