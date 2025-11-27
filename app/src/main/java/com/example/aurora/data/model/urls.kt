package com.example.aurora.data.model

object urls {
    const val baseURL = "http://127.0.0.1:8000"
    const val loginURL = "$baseURL/authentication/login/"
    const val signupURL = "$baseURL/authentication/register/"
    const val forgotPasswordURL = "$baseURL/authentication/forgot-password/"
    const val resetPasswordURL = "$baseURL/authentication/reset-password/"
    // const val refreshURL = "$baseURL/authentication/token/refresh"
    // const val obtainTokenURL = "$baseURL/authentication/token/"
    // const val logoutURL = "$baseURL/authentication/logout/"
    // const val userURL = "$baseURL/authentication/user/"
    // const val logoutURL = "$baseURL/authentication/logout/"
    const val registerDispenserURL = "$baseURL/api/register-dispenser/"
    // const val deleteDispenserURL = "$baseURL/api/delete-dispenser/<str:name>"
    // const val listAllUserDispensersURL = "$baseURL/api/list-all-user-dispensers/"
    // const val updatePillNameURL = "$baseURL/api/update-pill-name/"
    // const val updateDispenserNameURL = "$baseURL/api/update-dispenser-name/"
}