package com.example.djigit.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.djigit.features.forgotpass.ForgotPasswordScreen
import com.example.djigit.features.home.HomeScreen
import com.example.djigit.features.login.LoginScreen
import com.example.djigit.features.login.LoginViewModel
import com.example.djigit.features.signup.SignUpScreen
import org.koin.androidx.compose.getViewModel

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Routes.MainRoute.Login.route) {
        composable(route = Routes.MainRoute.Login.route) {
            val viewModel = getViewModel<LoginViewModel>()
            val loginData by viewModel.login.collectAsStateWithLifecycle()
            LoginScreen(navController,loginData, onEmailChange = {viewModel.email(it)}, onPasswordChange = {viewModel.password(it)} )
        }
        composable(route = Routes.MainRoute.ForgotPassword.route) {
            ForgotPasswordScreen()
        }
        composable(route = Routes.MainRoute.SignUp.route) {
            SignUpScreen()
        }
        composable(route = Routes.MainRoute.Home.route) {
            HomeScreen()
        }
//        composable(route = Routes.MainRoute.Google.route) {
//            HomeScreen()
//        }
//        composable(route = Routes.MainRoute.Facebook.route) {
//            HomeScreen()
//        }
    }
}