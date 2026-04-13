package com.example.aurora

import android.app.Application
import android.util.Log
import com.example.aurora.data.repository.TokenStorage
import com.example.aurora.di.networkModule
import com.example.aurora.domain.usecase.RegisterPushTokenUseCase
import com.example.aurora.notifications.NotificationHelper
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Log Koin into Android logger
            androidLogger(Level.DEBUG)
            // Reference Android context
            androidContext(this@MainApplication)
            // Load modules
            modules( networkModule, appModule )
        }

        NotificationHelper.createChannels(this)
        val firebaseApp = try {
            FirebaseApp.initializeApp(this)
        } catch (e: Exception) {
            Log.w("AuroraFCM", "Firebase init unavailable; skipping FCM startup", e)
            null
        }
        if (firebaseApp == null) {
            return
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("AuroraFCM", "Fetching FCM token failed", task.exception)
                return@addOnCompleteListener
            }
            val token = task.result ?: return@addOnCompleteListener
            val koin = org.koin.core.context.GlobalContext.get()
            val tokenStorage = koin.get<TokenStorage>()
            tokenStorage.savePushToken(token)
            if (tokenStorage.getAccessToken().isNullOrBlank()) return@addOnCompleteListener
            val registerPushTokenUseCase = koin.get<RegisterPushTokenUseCase>()
            CoroutineScope(Dispatchers.IO).launch {
                registerPushTokenUseCase(token).onFailure {
                    Log.e("AuroraFCM", "Push token registration failed: ${it.message}")
                }
            }
        }
    }
}