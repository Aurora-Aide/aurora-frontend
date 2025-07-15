package com.example.djigit.data

import retrofit2.Response

fun <T> requestBody(request: Response<T>):Result<T> {
    return if(request.isSuccessful){
        request.body()?.let {
            Result.success(it)
        } ?: Result.failure(Throwable(request.message()))
    } else {
        Result.failure(Throwable(request.errorBody().toString()))
    }
}