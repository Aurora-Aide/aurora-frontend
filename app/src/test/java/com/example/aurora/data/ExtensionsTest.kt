package com.example.aurora.data

import com.example.aurora.data.error.AppException
import java.io.IOException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class ExtensionsTest {
    @Test
    fun request_body_success_returns_success() {
        val result = requestBody(Response.success("ok"))
        assertTrue(result.isSuccess)
    }

    @Test
    fun request_body_error_returns_app_exception() {
        logDebug(
            message = "ExtensionsTest request_body_error_returns_app_exception start",
            hypothesisId = "H1",
            data = mapOf("status" to 400)
        )
        val body = "bad".toResponseBody("application/json".toMediaType())
        val result = requestBody<String>(Response.error(400, body))
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is AppException)
    }

    @Test
    fun safe_request_catches_exceptions() = runTest {
        logDebug(
            message = "ExtensionsTest safe_request_catches_exceptions start",
            hypothesisId = "H2",
            data = mapOf("case" to "IOException")
        )
        val result = safeRequest<String> { throw IOException("offline") }
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IOException)
    }

}
