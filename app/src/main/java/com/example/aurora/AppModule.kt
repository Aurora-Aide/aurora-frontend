package com.example.aurora

import com.example.aurora.data.repository.AuthRepository
import com.example.aurora.data.repository.AuthRepositoryImpl
import com.example.aurora.domain.usecase.AddDispenserUseCase
import com.example.aurora.domain.usecase.ForgotPassUseCase
import com.example.aurora.domain.usecase.GetUserUseCase
import com.example.aurora.domain.usecase.ListAllUserDispensersUseCase
import com.example.aurora.domain.usecase.DeleteUserUseCase
import com.example.aurora.domain.usecase.UpdateNamesUseCase
import com.example.aurora.domain.usecase.DeleteDispenserUseCase
import com.example.aurora.domain.usecase.LoginUseCase
import com.example.aurora.domain.usecase.LogoutUseCase
import com.example.aurora.domain.usecase.ResetPassUseCase
import com.example.aurora.domain.usecase.SignupUseCase
import com.example.aurora.domain.usecase.UpdatePillNameUseCase
import com.example.aurora.domain.usecase.UpdateDispenserNameUseCase
import com.example.aurora.domain.usecase.ListSchedulesUseCase
import com.example.aurora.domain.usecase.CreateScheduleUseCase
import com.example.aurora.domain.usecase.GetScheduleUseCase
import com.example.aurora.domain.usecase.UpdateScheduleUseCase
import com.example.aurora.domain.usecase.DeleteScheduleUseCase
import com.example.aurora.domain.usecase.GetDispenserUseCase
import com.example.aurora.domain.usecase.admin.AdminListUsersUseCase
import com.example.aurora.domain.usecase.admin.AdminListDispensersUseCase
import com.example.aurora.domain.usecase.admin.AdminListDispenserModelsUseCase
import com.example.aurora.domain.usecase.admin.AdminCreateDispenserModelUseCase
import com.example.aurora.features.dispenser.AddScheduleViewModel
import com.example.aurora.features.dispenser.ScheduleViewModel
import com.example.aurora.features.forgotPassword.ForgotViewModel
import com.example.aurora.features.home.AddDispenserViewModel
import com.example.aurora.features.home.HomeViewModel
import com.example.aurora.features.login.LoginViewModel
import com.example.aurora.features.admin.AdminHomeViewModel
import com.example.aurora.features.admin.AdminCreateDispenserModelViewModel
import com.example.aurora.features.profile.PersonalInformationViewModel
import com.example.aurora.features.profile.ProfileViewModel
import com.example.aurora.features.dispenser.DispenserViewModel
import com.example.aurora.features.dispenser.ContainerViewModel
import com.example.aurora.features.signup.SignupViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        LoginViewModel(get())
    }
    
    viewModel {
        ProfileViewModel(get(), get(), get())
    }

    viewModel {
        PersonalInformationViewModel(get(), get())
    }

    viewModel {
        SignupViewModel(get())
    }

    viewModel{
        ForgotViewModel(get(), get())
    }

    viewModel{
        AddDispenserViewModel(get(), get())
    }

    viewModel {
        HomeViewModel(get())
    }

    viewModel {
        DispenserViewModel(get(), get(), get(), get())
    }

    viewModel {
        AddScheduleViewModel(get(), get())
    }

    viewModel {
        ScheduleViewModel(get(), get(), get())
    }

    viewModel {
        ContainerViewModel(get(), get())
    }

    viewModel {
        AdminHomeViewModel(get(), get(), get())
    }

    viewModel {
        AdminCreateDispenserModelViewModel(get(), get())
    }


    factory{
        LoginUseCase(get())
    }

    factory{
        SignupUseCase(get())
    }

    factory{
        AddDispenserUseCase(get())
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

    factory{
        ListAllUserDispensersUseCase(get())
    }

    factory{
        DeleteUserUseCase(get())
    }

    factory{
        UpdateNamesUseCase(get())
    }

    factory{
        DeleteDispenserUseCase(get())
    }

    factory{
        UpdatePillNameUseCase(get())
    }

    factory{
        UpdateDispenserNameUseCase(get())
    }

    factory { 
        ListSchedulesUseCase(get()) 
    }

    factory {
         CreateScheduleUseCase(get())
    }

    factory { 
        GetScheduleUseCase(get()) 
    }

    factory {
         UpdateScheduleUseCase(get())
    }

    factory { 
        DeleteScheduleUseCase(get()) 
    }

    factory { 
        GetDispenserUseCase(get())
    }

    factory {
        GetUserUseCase(get())
    }

    // admin
    factory {
        AdminListUsersUseCase(get())
    }

    factory {
        AdminListDispensersUseCase(get())
    }

    factory {
        AdminListDispenserModelsUseCase(get())
    }

    factory {
        AdminCreateDispenserModelUseCase(get())
    }

    single<AuthRepository>{
        AuthRepositoryImpl(get(), get())
    }
}