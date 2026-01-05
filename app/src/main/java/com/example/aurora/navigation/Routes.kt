package com.example.aurora.navigation

import android.net.Uri
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

        data object Home : Routes("${MainRoute.route}/home?name={name}&id={id}") {
            fun NavController.toHome(name: String) = navigate("${MainRoute.route}/home?name=${Uri.encode(name)}")
        }

        data object AddDispenser : Routes("${MainRoute.route}/addDispenser") {
            fun NavController.toAddDispenser() = navigate("${MainRoute.route}/addDispenser")
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

        data object Settings: Routes("${MainRoute.route}/settings") {
            fun NavController.toSettings() = navigate("${MainRoute.route}/settings")
        }

        data object Dispenser: Routes("${MainRoute.route}/dispenser?id={id}&name={name}") {
            fun NavController.toDispenser(id: String, name: String) = navigate("${MainRoute.route}/dispenser?id=$id&name=${Uri.encode(name)}")
        }

        data object Container: Routes("${MainRoute.route}/container?dispenserId={dispenserId}&dispenserName={dispenserName}&slot={slot}&pillName={pillName}&containerId={containerId}") {
            fun NavController.toContainer(dispenserId: String, dispenserName: String, slot: Int, pillName: String, containerId: Int) =
                navigate("${MainRoute.route}/container?dispenserId=$dispenserId&dispenserName=${Uri.encode(dispenserName)}&slot=$slot&pillName=${Uri.encode(pillName)}&containerId=$containerId")
        }

        data object AddSchedule: Routes("${MainRoute.route}/addSchedule?containerId={containerId}") {
            fun NavController.toAddSchedule(containerId: Int) =
                navigate("${MainRoute.route}/addSchedule?containerId=$containerId")
        }

        data object Schedule: Routes("${MainRoute.route}/schedule") {
            fun NavController.toSchedule() = navigate("${MainRoute.route}/schedule")
        }
    }
}