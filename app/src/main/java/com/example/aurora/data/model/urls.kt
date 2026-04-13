package com.example.aurora.data.model

object urls {
    const val baseURL = "http://192.168.1.131:8000"

    //authentication
    const val loginURL = "$baseURL/authentication/login/"
    const val signupURL = "$baseURL/authentication/register/"
    const val forgotPasswordURL = "$baseURL/authentication/forgot-password/"
    const val resetPasswordURL = "$baseURL/authentication/reset-password/"
    const val refreshURL = "$baseURL/authentication/token/refresh/"

    //profile
    const val userURL = "$baseURL/authentication/user/" // get user email first and last name
    const val logoutURL = "$baseURL/authentication/logout/"
    const val deleteUserURL = "$baseURL/authentication/user/delete/"
    const val updateNamesURL = "$baseURL/authentication/update-names/"

    //dispenser
    const val registerDispenserURL = "$baseURL/api/register-dispenser/"
    const val deleteDispenserURL = "$baseURL/api/delete-dispenser/"
    const val listAllUserDispensersURL = "$baseURL/api/list-all-user-dispensers/"
    const val updateDispenserNameURL = "$baseURL/api/update-dispenser-name/"
    const val getDispenserURL = "$baseURL/api/dispenser/"

    //container
    const val updatePillNameURL = "$baseURL/api/update-pill-name/"
    const val schedulesByContainerBaseURL = "$baseURL/api/containers/"
    const val dispenseNowByContainerBaseURL = "$baseURL/api/containers/"
    const val schedulesBaseURL = "$baseURL/api/schedules/"
    const val registerPushTokenURL = "$baseURL/api/notifications/push-token/register/"
    const val deactivatePushTokenURL = "$baseURL/api/notifications/push-token/deactivate/"

    // admin
    const val adminUsersURL = "$baseURL/api/admin/users/"
    const val adminDispensersURL = "$baseURL/api/admin/dispensers/"
    const val adminDispenserModelsURL = "$baseURL/api/admin/dispenser-models/"
}