package com.example.aurora.data.repository

import android.content.Context
import android.util.Log
class TokenStorage(context: Context) {

    private val prefs = context.getSharedPreferences("auth_tokens", Context.MODE_PRIVATE)

    private companion object {
        const val KEY_ACCESS = "access"
        const val KEY_REFRESH = "refresh"
        const val TAG = "TokenStorage"
    }

    fun saveTokens(access: String, refresh: String) {
        val success = prefs.edit()
            .putString(KEY_ACCESS, access)
            .putString(KEY_REFRESH, refresh)
            .apply()
        Log.d(TAG, "saveTokens: success=$success, accessLen=${access.length}, refreshLen=${refresh.length}")
    }

    fun saveAccessToken(access: String) {
        val success = prefs.edit().putString(KEY_ACCESS, access).apply()
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

    fun clearTokens() {
        prefs.edit().clear().apply()
        Log.d(TAG, "clearTokens")
    }
}
