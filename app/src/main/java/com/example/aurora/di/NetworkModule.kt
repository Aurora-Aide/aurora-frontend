package com.example.aurora.di

import com.example.aurora.data.api.RetrofitAPI
import com.example.aurora.data.sorce.AuthInterceptor
import com.example.aurora.data.model.AuthDataSourceImpl
import com.example.aurora.data.model.urls
import com.example.aurora.data.repository.TokenAuthenticator
import com.example.aurora.data.repository.TokenStorage
import com.example.aurora.data.sorce.AuthDataSource
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { TokenStorage(androidContext()) }

    single {
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    single(named("authOkHttp")) {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    single(named("authRetrofit")) {
        Retrofit.Builder()
            .baseUrl(urls.baseURL)
            .client(get(named("authOkHttp")))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single(named("authApi")) {
        get<Retrofit>(named("authRetrofit")).create(RetrofitAPI::class.java)
    }

    single {
        TokenAuthenticator(
            tokenStorage = get(),
            api = get(named("authApi")), // Use auth API (without auth interceptor)
        )
    }

    single(named("apiOkHttp")) {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(AuthInterceptor(get()))     // adds access token
            .authenticator(get<TokenAuthenticator>())   // refresh on 401
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    single(named("apiRetrofit")) {
        Retrofit.Builder()
            .baseUrl(urls.baseURL)
            .client(get(named("apiOkHttp")))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<RetrofitAPI> {
        get<Retrofit>(named("apiRetrofit")).create(RetrofitAPI::class.java)
    }

    single<AuthDataSource> {
        AuthDataSourceImpl(get<RetrofitAPI>())
    }
}
