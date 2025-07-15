package com.example.djigit.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.djigit.features.forgotPassword.ForgotPasswordScreen
import com.example.djigit.features.forgotPassword.ForgotViewModel
import com.example.djigit.features.google.GoogleScreen
import com.example.djigit.features.home.HomeScreen
import com.example.djigit.features.login.LoginScreen
import com.example.djigit.features.login.LoginViewModel
import com.example.djigit.features.profile.PersonalInfoScreen
import com.example.djigit.features.profile.PersonalInformationViewModel
import com.example.djigit.features.profile.ProfileScreen
import com.example.djigit.features.profile.ProfileViewModel
import com.example.djigit.features.signup.SignupScreen
import com.example.djigit.features.signup.SignupViewModel
import com.example.djigit.navigation.Routes.MainRoute.Login.toLogIn
import com.example.djigit.navigation.Routes.MainRoute.ForgotPassword.toForgotPassword
import com.example.djigit.navigation.Routes.MainRoute.PersonalInformation.toPersonalInformation
import com.example.djigit.navigation.Routes.MainRoute.Profile.toProfile
import com.example.djigit.navigation.Routes.MainRoute.SignUp.toSignUp
import org.koin.androidx.compose.getViewModel

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Routes.MainRoute.Login.route) {
        composable(route = Routes.MainRoute.Login.route) {
            val viewModel = getViewModel<LoginViewModel>()
            val loginData by viewModel.login.collectAsStateWithLifecycle()
            LoginScreen(
                loginData,
                onEmailChange = { viewModel.email(it) },
                onPasswordChange = { viewModel.password(it) },
                onLoginClick = { viewModel.validate() },
                isLoginSuccessful = {
                    Log.d("TAG", "to profile")
                    viewModel.resetLogin()
                    navController.toProfile()

                },
                onForgotPasswordClick = { navController.toForgotPassword() },
                onSignUpClick = { navController.toSignUp() }
            )
        }
        composable(route = Routes.MainRoute.ForgotPassword.route) {
            val viewModelPass = getViewModel<ForgotViewModel>()
            val data by viewModelPass.data.collectAsStateWithLifecycle()
            ForgotPasswordScreen(
                navController,
                data,
                onEmailChange = {viewModelPass.email(it)},
                onPasswordChange = {viewModelPass.password(it)},
                onRepeatChange = {viewModelPass.repeat(it)},
                onSendClick = {viewModelPass.validateEmail()},
                onResetClick = {viewModelPass.validatePassword()},
                isResetSuccessful = {},
                onBackClick = {navController.toLogIn()},
            )
        }
        composable(route = Routes.MainRoute.SignUp.route) {
            val viewModelSignup = getViewModel<SignupViewModel>()
            val signupData by viewModelSignup.signup.collectAsStateWithLifecycle()
            SignupScreen(
                navController,
                signupData,
                onEmailChange = { viewModelSignup.email(it) },
                onPasswordChange = { viewModelSignup.password(it) },
                onFirstNameChange = { viewModelSignup.firstName(it) },
                onLastNameChange = { viewModelSignup.lastName(it) },
                onSignupClick = { viewModelSignup.validate() },
                isSignupSuccessful = { viewModelSignup.resetSignup() },
                onBrandChange = { viewModelSignup.brand(it) },
                onModelChange = { viewModelSignup.model(it) },
                onLicensePlateChange = { viewModelSignup.licensePlate(it) },
                onToHomeClick = { viewModelSignup.signup() },
                onBackClick = { viewModelSignup.onBackClick() }
            )
        }
        composable(route = Routes.MainRoute.Home.route) {
            HomeScreen()
        }
        composable(route = Routes.MainRoute.Google.route) {
            GoogleScreen()
        }
        composable(route = Routes.MainRoute.Profile.route) {
            val viewModel = getViewModel<ProfileViewModel>()
            val showHide by viewModel.showPopUp.collectAsStateWithLifecycle()
            ProfileScreen(
                showPopup = showHide,
                onLogOutClicked = { viewModel.showHide() },
                onPersonalInformation = { navController.toPersonalInformation() }
            )
        }
        composable(route = Routes.MainRoute.PersonalInformation.route) {
            val viewModel = getViewModel<PersonalInformationViewModel>()
            val personalInformationData by viewModel.personalInformation.collectAsStateWithLifecycle()

            PersonalInfoScreen(
                personalInformationData = personalInformationData,
                onEmailChange = { viewModel.email(it) },
                onPasswordChange = { viewModel.password(it) },
                onLastNameChange = { viewModel.lastName(it) },
                onFirstNameChange = { viewModel.firstName(it) },
                onDeleteAccountClick = {},
                onPasswordVisibilityChange = { viewModel.isPasswordVisible() },
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}