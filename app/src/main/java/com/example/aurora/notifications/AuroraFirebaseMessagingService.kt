package com.example.aurora.notifications

import android.util.Log
import com.example.aurora.data.repository.TokenStorage
import com.example.aurora.domain.usecase.RegisterPushTokenUseCase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.context.GlobalContext

class AuroraFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        val koin = GlobalContext.getOrNull() ?: return
        val tokenStorage = koin.get<TokenStorage>()
        tokenStorage.savePushToken(token)
        if (tokenStorage.getAccessToken().isNullOrBlank()) return
        val registerPushTokenUseCase = koin.get<RegisterPushTokenUseCase>()
        CoroutineScope(Dispatchers.IO).launch {
            registerPushTokenUseCase(token).onFailure {
                Log.e("AuroraFCM", "Failed to register new push token: ${it.message}")
            }
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val title = message.notification?.title ?: message.data["title"] ?: "Aurora update"
        val body = message.notification?.body ?: message.data["body"] ?: "A new dispenser event was received."
        NotificationHelper.showEventNotification(applicationContext, title, body)
    }
}
