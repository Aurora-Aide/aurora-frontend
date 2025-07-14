package com.example.djigit

import com.example.djigit.data.api.RetrofitAPI
import com.example.djigit.data.model.AuthDataSourceImpl
import com.example.djigit.data.model.urls
import com.example.djigit.data.repository.AuthRepository
import com.example.djigit.data.repository.AuthRepositoryImpl
import com.example.djigit.data.sorce.AuthDataSource
import com.example.djigit.domain.usecase.LoginUseCase
import com.example.djigit.features.login.LoginViewModel
import com.example.djigit.features.profile.ProfileViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    viewModel {
        LoginViewModel(get())
    }

    viewModel { ProfileViewModel() }

    factory{
        LoginUseCase(get())
    }

    single<AuthRepository>{
        AuthRepositoryImpl(get())
    }

    single<AuthDataSource>{
        AuthDataSourceImpl(get())
    }

    single<RetrofitAPI>{
        get<Retrofit>().create(RetrofitAPI::class.java)
    }


   single<OkHttpClient> {
       val interceptor = HttpLoggingInterceptor()
       interceptor.level = HttpLoggingInterceptor.Level.BODY

        OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    single{
        val okHttpClient = get<OkHttpClient>()
        Retrofit.Builder()
            .baseUrl(urls.baseURL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}