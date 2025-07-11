package com.example.djigit.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.djigit.features.forgotPassword.ForgotPasswordScreen
import com.example.djigit.features.google.GoogleScreen
import com.example.djigit.features.home.HomeScreen
import com.example.djigit.features.login.LoginScreen
import com.example.djigit.features.login.LoginViewModel
import com.example.djigit.features.signup.SignupData
import com.example.djigit.features.signup.SignupScreen
import com.example.djigit.features.signup.SignupViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Routes.MainRoute.Login.route) {
        composable(route = Routes.MainRoute.Login.route) {
            val viewModelLogin = getViewModel<LoginViewModel>()
            val loginData by viewModelLogin.login.collectAsStateWithLifecycle()
            LoginScreen(navController,
                loginData,
                onEmailChange = {viewModelLogin.email(it)},
                onPasswordChange = {viewModelLogin.password(it)},
                onLoginClick = {viewModelLogin.validate()},
                isLoginSuccessful = {viewModelLogin.resetLogin()} )
        }
        composable(route = Routes.MainRoute.ForgotPassword.route) {
            ForgotPasswordScreen()
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
                onBackClick = {viewModelSignup.onBackClick(signupData.isFirstStep)}
                //onAddCarClick = TODO(),
            )
        }
        composable(route = Routes.MainRoute.Home.route) {
            HomeScreen()
        }
        composable(route = Routes.MainRoute.Google.route) {
            GoogleScreen()
        }
    }
}