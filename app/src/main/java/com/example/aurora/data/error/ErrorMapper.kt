package com.example.aurora.data.error

import java.io.IOException
import java.net.SocketTimeoutException

fun Throwable.toUiMessage(): String {
    return when (this) {
        is AppException -> apiError.message
        is SocketTimeoutException -> "Request timed out. Please try again."
        is IOException -> "Network error. Check your connection."
        else -> message ?: "Something went wrong. Please try again."
    }
}
