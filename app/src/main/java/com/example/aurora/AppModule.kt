package com.example.aurora

import com.example.aurora.data.api.RetrofitAPI
import com.example.aurora.data.model.AuthDataSourceImpl
import com.example.aurora.data.model.urls
import com.example.aurora.data.repository.AuthRepository
import com.example.aurora.data.repository.AuthRepositoryImpl
import com.example.aurora.data.sorce.AuthDataSource
import com.example.aurora.domain.usecase.DispenserUseCase
import com.example.aurora.domain.usecase.ForgotPassUseCase
import com.example.aurora.domain.usecase.LoginUseCase
import com.example.aurora.domain.usecase.LogoutUseCase
import com.example.aurora.domain.usecase.ResetPassUseCase
import com.example.aurora.domain.usecase.SignupUseCase
import com.example.aurora.features.forgotPassword.ForgotViewModel
import com.example.aurora.features.login.LoginViewModel
import com.example.aurora.features.profile.PersonalInformationViewModel
import com.example.aurora.features.profile.ProfileViewModel
import com.example.aurora.features.signup.SignupViewModel
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
    
    viewModel {
        ProfileViewModel(get())
    }

    viewModel {
        PersonalInformationViewModel()
    }

    viewModel {
        SignupViewModel(get())
    }

    viewModel{
        ForgotViewModel(get(), get())
    }


    factory{
        LoginUseCase(get())
    }

    factory{
        SignupUseCase(get())
    }

    factory{
        DispenserUseCase(get())
    }
    factory{
        ForgotPassUseCase(get())
    }
    factory{
        ResetPassUseCase(get())
    }

    factory{
        LogoutUseCase(get())
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