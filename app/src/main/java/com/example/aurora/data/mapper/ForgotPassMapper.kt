package com.example.aurora.data.mapper

import com.example.aurora.data.entity.ForgotPassEntity
import com.example.aurora.data.model.ForgotPass

fun ForgotPass.toForgotPassMapper(): ForgotPassEntity {
    return ForgotPassEntity(
       massage = this.massage.orEmpty()
    )
}