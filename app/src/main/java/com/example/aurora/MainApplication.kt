package com.example.aurora

import android.app.Application
import com.example.aurora.di.networkModule
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
    }
}