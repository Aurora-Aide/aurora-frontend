package com.example.aurora.data.repository

import com.example.aurora.data.api.RetrofitAPI
import com.example.aurora.data.model.Refresh
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Route

class TokenAuthenticator(
    private val tokenStorage: TokenStorage,
    private val api: RetrofitAPI 
) : Authenticator {

    override fun authenticate(route: Route?, response: okhttp3.Response): Request? {
        // Avoid trying to authenticate auth endpoints themselves to prevent loops.
        val path = response.request.url.encodedPath
        val isAuthCall = path.contains("/authentication/login")
                || path.contains("/authentication/signup")
                || path.contains("/authentication/token/refresh")
                || path.contains("/authentication/reset")
                || path.contains("/authentication/forgot")
        if (isAuthCall) return null

        if (responseCount(response) >= 2) return null

        val refresh = tokenStorage.getRefreshToken() ?: return null

        val newAccess = runBlocking {
            val refreshResp = api.refreshToken(Refresh(refresh))
            
            if (!refreshResp.isSuccessful) {
                // Refresh failed: clear tokens to avoid retrying with bad creds.
                tokenStorage.clearTokens()
                return@runBlocking null
            }
            
            val access = refreshResp.body()?.access ?: return@runBlocking null
            
            tokenStorage.saveAccessToken(access)
            access
        } ?: return null

        return response.request.newBuilder()
            .header("Authorization", "Bearer $newAccess")
            .build()
    }

    private fun responseCount(response: okhttp3.Response): Int {
        var r: okhttp3.Response? = response
        var count = 1
        while (r?.priorResponse != null) { count++; r = r.priorResponse }
        return count
    }
}
