package com.he1extg.converterapi.exception

import java.io.IOException

private const val TTS_EMPTY_STRING_EXCEPTION_MESSAGE = "An empty string was passed to the TTS module."

class TtsEmptyStringException(
    override val message: String = TTS_EMPTY_STRING_EXCEPTION_MESSAGE
) : IOException(message)