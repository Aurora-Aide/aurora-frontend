package com.example.djigit.data

import com.example.djigit.data.model.DataModel
import retrofit2.Call
import retrofit2.http.*

interface RetrofitAPI {

    // POST annotation used to make a post request and pass parameter
    @POST("users")
    fun postData(@Body dataModel: DataModel?): Call<DataModel?>?

    // In line above we are creating a method to post our data.
}