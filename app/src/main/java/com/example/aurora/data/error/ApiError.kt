package com.example.aurora.data.error

data class ApiError(
    val message: String,
    val statusCode: Int? = null,
    val fieldErrors: Map<String, List<String>> = emptyMap(),
    val isAuthError: Boolean = false,
)

class AppException(val apiError: ApiError) : RuntimeException(apiError.message)
