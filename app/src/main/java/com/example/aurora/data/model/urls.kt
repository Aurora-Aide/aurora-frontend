package com.example.aurora.data.model

object urls {
    const val baseURL = "http://192.168.0.114:8000"
    const val loginURL = "$baseURL/authentication/login/"
    const val signupURL = "$baseURL/authentication/register/"
    const val forgotPasswordURL = "$baseURL/authentication/forgot-password/"
    const val resetPasswordURL = "$baseURL/authentication/reset-password/"
    const val refreshURL = "$baseURL/authentication/token/refresh/"
    // const val obtainTokenURL = "$baseURL/authentication/token/"
    const val userURL = "$baseURL/authentication/user/" // get user email first and last name
    const val logoutURL = "$baseURL/authentication/logout/"
    const val registerDispenserURL = "$baseURL/api/register-dispenser/"
    const val deleteDispenserURL = "$baseURL/api/delete-dispenser/"
    const val listAllUserDispensersURL = "$baseURL/api/list-all-user-dispensers/"
    const val updatePillNameURL = "$baseURL/api/update-pill-name/"
    const val updateDispenserNameURL = "$baseURL/api/update-dispenser-name/"
    const val deleteUserURL = "$baseURL/authentication/user/delete/"
    const val updateNamesURL = "$baseURL/authentication/update-names/"
}