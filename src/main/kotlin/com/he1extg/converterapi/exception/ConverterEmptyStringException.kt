package com.he1extg.converterapi.exception

import java.io.IOException

class ConverterEmptyStringException(
    override val message: String = "An empty string was passed to the TTS module."
) : IOException(message)