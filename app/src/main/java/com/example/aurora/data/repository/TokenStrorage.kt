package com.example.aurora.data.repository

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TokenStorage(context: Context) {

    private val prefs = context.getSharedPreferences("auth_tokens", Context.MODE_PRIVATE)

    private companion object {
        const val KEY_ACCESS = "access"
        const val KEY_REFRESH = "refresh"
        const val TAG = "TokenStorage"
    }

    suspend fun saveTokens(access: String, refresh: String) = withContext(Dispatchers.IO) {
        val success = prefs.edit()
            .putString(KEY_ACCESS, access)
            .putString(KEY_REFRESH, refresh)
            .commit() // commit() is safe here since we're on IO dispatcher
        Log.d(TAG, "saveTokens: success=$success, accessLen=${access.length}, refreshLen=${refresh.length}")
    }

    suspend fun saveAccessToken(access: String) = withContext(Dispatchers.IO) {
        val success = prefs.edit().putString(KEY_ACCESS, access).commit()
        Log.d(TAG, "saveAccessToken: success=$success, accessLen=${access.length}")
    }

    fun getAccessToken(): String? {
        val token = prefs.getString(KEY_ACCESS, null)
        Log.d(TAG, "getAccessToken: token=${if (token != null) "exists (len=${token.length})" else "null"}")
        return token
    }
    
    fun getRefreshToken(): String? {
        val token = prefs.getString(KEY_REFRESH, null)
        Log.d(TAG, "getRefreshToken: token=${if (token != null) "exists (len=${token.length})" else "null"}")
        return token
    }

    suspend fun clearTokens() = withContext(Dispatchers.IO) {
        prefs.edit().clear().apply()
        Log.d(TAG, "clearTokens")
    }
}
