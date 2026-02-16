package com.example.aurora.data

import com.example.aurora.data.error.ApiError
import com.example.aurora.data.error.AppException
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response

fun <T> requestBody(request: Response<T>): Result<T> {
    return if (request.isSuccessful) {
        request.body()?.let {
            Result.success(it)
        } ?: Result.failure(AppException(ApiError(message = request.message(), statusCode = request.code())))
    } else {
        Result.failure(AppException(parseApiError(request)))
    }
}

@Suppress("TooGenericExceptionCaught")
suspend fun <T> safeRequest(block: suspend () -> Response<T>): Result<T> {
    return try {
        requestBody(block())
    } catch (error: Throwable) {
        Result.failure(error)
    }
}

@Suppress("TooGenericExceptionCaught")
suspend fun <T> safeResponse(block: suspend () -> Response<T>): Result<Response<T>> {
    return try {
        Result.success(block())
    } catch (error: Throwable) {
        Result.failure(error)
    }
}

fun parseApiError(response: Response<*>): ApiError {
    val code = response.code()
    val raw = response.errorBody()?.string().orEmpty().trim()
    if (raw.isEmpty()) {
        return ApiError(
            message = response.message().ifBlank { "Request failed" },
            statusCode = code,
            isAuthError = code == 401 || code == 403
        )
    }

    return try {
        val json = JSONObject(raw)
        val fieldErrors = mutableMapOf<String, List<String>>()

        if (json.has("detail")) {
            val msg = json.optString("detail").ifBlank { "Request failed" }
            return ApiError(message = msg, statusCode = code, isAuthError = code == 401 || code == 403)
        }

        if (json.has("message")) {
            val msg = json.optString("message").ifBlank { "Request failed" }
            return ApiError(message = msg, statusCode = code, isAuthError = code == 401 || code == 403)
        }

        val keys = json.keys()
        while (keys.hasNext()) {
            val key = keys.next()
            when (val value = json.get(key)) {
                is JSONArray -> {
                    val list = mutableListOf<String>()
                    for (i in 0 until value.length()) {
                        list.add(value.optString(i))
                    }
                    fieldErrors[key] = list
                }
                else -> fieldErrors[key] = listOf(value.toString())
            }
        }

        val firstMessage = fieldErrors.values.flatten().firstOrNull().orEmpty()
        ApiError(
            message = if (firstMessage.isNotEmpty()) firstMessage else "Request failed",
            statusCode = code,
            fieldErrors = fieldErrors,
            isAuthError = code == 401 || code == 403
        )
    } catch (_: Exception) {
        ApiError(
            message = "Request failed",
            statusCode = code,
            isAuthError = code == 401 || code == 403
        )
    }
}