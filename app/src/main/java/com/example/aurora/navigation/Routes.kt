package com.example.aurora.navigation

import androidx.navigation.NavController

sealed class Routes(val route: String) {

    data object MainRoute : Routes("mainRoutes") {

        data object Login : Routes("${MainRoute.route}/login") {
            fun NavController.toLogIn() = navigate("${MainRoute.route}/login")
        }

        data object ForgotPassword : Routes("${MainRoute.route}/forgotPassword") {
            fun NavController.toForgotPassword() = navigate("${MainRoute.route}/forgotPassword")

        }

        data object SignUp : Routes("${MainRoute.route}/signUp") {
            fun NavController.toSignUp() = navigate("${MainRoute.route}/signUp")

        }

        data object Home : Routes("${MainRoute.route}/home") {
            fun NavController.toHome() = navigate("${MainRoute.route}/home")

        }
        data object Google: Routes("${MainRoute.route}/google") {
            fun NavController.toGoogle() = navigate("${MainRoute.route}/google")

        }
        data object Profile: Routes("${MainRoute.route}/profile") {
            fun NavController.toProfile() = navigate("${MainRoute.route}/profile")
        }
        data object PersonalInformation: Routes("${MainRoute.route}/personalInformation") {
            fun NavController.toPersonalInformation() = navigate("${MainRoute.route}/personalInformation")
        }
    }
}