package com.example.aurora.data

import com.example.aurora.data.error.AppException
import java.io.File
import java.io.IOException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class ExtensionsTest {
    @Before
    fun initLog() {
        logDebug(
            message = "ExtensionsTest initLog",
            hypothesisId = "H0",
            data = mapOf("phase" to "before")
        )
    }
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

    private fun logDebug(message: String, hypothesisId: String, data: Map<String, Any?>) {
        // #region agent log
        val payload = buildString {
            append("{")
            append("\"id\":\"log_")
            append(System.currentTimeMillis())
            append("_")
            append(hypothesisId)
            append("\",")
            append("\"timestamp\":")
            append(System.currentTimeMillis())
            append(",")
            append("\"location\":\"ExtensionsTest.kt\",")
            append("\"message\":\"")
            append(message.replace("\"", "\\\""))
            append("\",")
            append("\"data\":")
            append(data.entries.joinToString(prefix = "{", postfix = "}") {
                val value = it.value
                val serialized = when (value) {
                    null -> "null"
                    is Number, is Boolean -> value.toString()
                    else -> "\"${value.toString().replace("\"", "\\\"")}\""
                }
                "\"${it.key}\":$serialized"
            })
            append(",")
            append("\"runId\":\"pre-fix\",")
            append("\"hypothesisId\":\"")
            append(hypothesisId)
            append("\"")
            append("}")
        }
        val logFile = File("c:\\\\Users\\\\kalit\\\\OneDrive\\\\Desktop\\\\aurora\\\\.cursor\\\\debug.log")
        logFile.parentFile?.mkdirs()
        logFile.appendText(payload + System.lineSeparator())
        require(logFile.exists()) { "Debug log was not created at ${logFile.absolutePath}" }
        // #endregion
    }
}
