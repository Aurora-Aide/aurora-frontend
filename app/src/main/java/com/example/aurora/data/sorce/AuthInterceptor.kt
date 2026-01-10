package com.example.aurora.data.sorce

import android.util.Log
import com.example.aurora.data.repository.TokenStorage
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenStorage: TokenStorage) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val path = original.url.encodedPath

        // Do NOT add Authorization for auth endpoints (login/signup/refresh/etc.)
        val isAuthCall = path.contains("/authentication/login")
                || path.contains("/authentication/signup")
                || path.contains("/authentication/token/refresh")
                || path.contains("/authentication/reset")
                || path.contains("/authentication/forgot")

        if (isAuthCall) {
            return chain.proceed(original)
        }

        val access = tokenStorage.getAccessToken()
        Log.d("AuthInterceptor", "access is null? ${access.isNullOrBlank()}")

        val request = if (!access.isNullOrBlank()) {
            original.newBuilder()
                .addHeader("Authorization", "Bearer $access")
                .build()
        } else original

        return chain.proceed(request)
    }
}
