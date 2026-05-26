package com.example.aurora.data.error

import androidx.annotation.StringRes
import com.example.aurora.R
import java.io.IOException
import java.net.SocketTimeoutException

@StringRes
fun Throwable.toUiMessageRes(): Int {
    return when (this) {
        is AppException -> R.string.error_request_failed
        is SocketTimeoutException -> R.string.error_time_out
        is IOException -> R.string.error_network
        else -> R.string.error_something_went_wrong
    }
}
