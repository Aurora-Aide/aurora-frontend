package com.example.aurora.features.home

import com.example.aurora.data.model.Dispenser

data class DispensersData(
    val dispensers: List<Dispenser> = emptyList(),
    val errorMessage: String = ""
)
