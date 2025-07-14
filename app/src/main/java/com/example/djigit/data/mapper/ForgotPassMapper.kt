package com.example.djigit.data.mapper

import com.example.djigit.data.entity.CarEntity
import com.example.djigit.data.entity.ForgotPassEntity
import com.example.djigit.data.model.Cars
import com.example.djigit.data.model.ForgotPass

fun ForgotPass.toForgotPassMapper(): ForgotPassEntity {
    return ForgotPassEntity(
       massage = this.massage.orEmpty()
    )
}