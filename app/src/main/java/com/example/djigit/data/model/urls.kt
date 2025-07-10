package com.example.djigit.data.model

object urls {
    const val baseURL = "https://stamsoft-backend-zwjo.onrender.com"
    const val loginURL = "$baseURL/auth/login"
    const val signupURL = "$baseURL/auth/signup"
    const val googleURL = "$baseURL/auth/google"
    const val forgotPasswordURL = "$baseURL/auth/forgot-password"
    const val resetPasswordURL = "$baseURL/auth/reset-password"
    const val facebookURL = "$baseURL/auth/facebook"
    const val facebookCallbackURL = "$baseURL/auth/facebook/callback"
    const val refreshURL = "$baseURL/auth/refresh"
    const val logoutURL = "$baseURL/auth/logout"
}