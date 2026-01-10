package com.example.aurora.data.model

data class UpdatePillNameRequest(
    val dispenser_name: String,
    val slot_number: Int,
    val pill_name: String,
)

