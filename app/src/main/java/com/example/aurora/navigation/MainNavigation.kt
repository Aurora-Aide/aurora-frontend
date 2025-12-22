package com.example.aurora.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aurora.features.forgotPassword.ForgotPasswordScreen
import com.example.aurora.features.forgotPassword.ForgotViewModel
import com.example.aurora.features.google.GoogleScreen
import com.example.aurora.features.home.HomeScreen
import com.example.aurora.features.login.LoginScreen
import com.example.aurora.features.login.LoginViewModel
import com.example.aurora.features.profile.PersonalInfoScreen
import com.example.aurora.features.profile.PersonalInformationViewModel
import com.example.aurora.features.profile.ProfileScreen
import com.example.aurora.features.profile.ProfileViewModel
import com.example.aurora.features.settings.SettingsScreen
import com.example.aurora.features.signup.SignupScreen
import com.example.aurora.features.signup.SignupViewModel
import com.example.aurora.navigation.Routes.MainRoute.Login.toLogIn
import com.example.aurora.navigation.Routes.MainRoute.ForgotPassword.toForgotPassword
import com.example.aurora.navigation.Routes.MainRoute.Google.toGoogle
import com.example.aurora.navigation.Routes.MainRoute.Home.toHome
import com.example.aurora.navigation.Routes.MainRoute.PersonalInformation.toPersonalInformation
import com.example.aurora.navigation.Routes.MainRoute.Profile.toProfile
import com.example.aurora.navigation.Routes.MainRoute.Settings.toSettings
import com.example.aurora.navigation.Routes.MainRoute.SignUp.toSignUp
import org.koin.androidx.compose.getViewModel

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Routes.MainRoute.Home.route) {
        composable(route = Routes.MainRoute.Login.route) {
            val viewModelLogin = getViewModel<LoginViewModel>()
            val loginData by viewModelLogin.login.collectAsStateWithLifecycle()
            LoginScreen(
                loginData,
                onEmailChange = { viewModelLogin.email(it) },
                onPasswordChange = { viewModelLogin.password(it) },
                onLoginClick = { viewModelLogin.validate() },
                isLoginSuccessful = {
                    Log.d("TAG", "to home ${loginData.email}")
                    viewModelLogin.resetLogin()
                    navController.toHome(loginData.firstName, "")  //TODO id

                },
                onForgotPasswordClick = { navController.toForgotPassword() },
                onSignUpClick = { navController.toSignUp() },
                onGoogleClick =  { navController.toGoogle()}
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
                onToHomeClick = {
                    Log.d("TAG", "to home")
                    viewModelSignup.resetSignup()
                    viewModelSignup.signup()
                    navController.toHome(signupData.firstName, "") // TODO id

                },
                onBackClick = { viewModelSignup.onBackClick() },
                onSecondPasswordChange = { viewModelSignup.passwordRepeat(it) },
                onOneClick = {},
                onTwoClick = {},
            )
        }
        composable(route = Routes.MainRoute.Home.route) { navBackStackEntry ->
            val name = navBackStackEntry.arguments?.getString("name")
            HomeScreen(
                onToProfileClick = { navController.toProfile("", "", "", "") },  //TODO use actual data
                name = name.orEmpty(),
            )
        }
        composable(route = Routes.MainRoute.Google.route) {
            GoogleScreen()
        }
        composable(route = Routes.MainRoute.Settings.route) {
            SettingsScreen()
        }
        composable(route = Routes.MainRoute.Profile.route) {
            val viewModel = getViewModel<ProfileViewModel>()
            val showHideLogOut by viewModel.showPopUpLogOut.collectAsStateWithLifecycle()
            val showHideDelete by viewModel.showPopUpDelete.collectAsStateWithLifecycle()
            ProfileScreen(
                showPopupLogOut = showHideLogOut,
                showPopupDelete = showHideDelete,
                onLogOutClicked = { viewModel.showHideLogOut(); navController.toLogIn() },  //log out TODO
                onDeleteAccountClicked = { navController.toSignUp(); viewModel.showHideDelete() },  // delete account TODO
                onPersonalInformation = { navController.toPersonalInformation() },
                onBackToProfileLogClicked = { viewModel.showHideLogOutBack() },  //hide popup
                onBackToProfileDeleteClicked = { viewModel.showHideDeleteBack()},  //hide popup
                onSettings = { navController.toSettings() },
                onLogOut = { viewModel.showHideLogOutBack() }, // show popup
                onDeleteAccount = { viewModel.showHideDeleteBack() }, //sho popup
                onToHomeClick = { navController.toHome("name", "") } // TODO find id
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