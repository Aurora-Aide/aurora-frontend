package com.example.aurora.features.home

data class DispenserData (
    val id: String = "",
    val name: String = "",
    val isAddDispenserSuccessful: Boolean = false,
    val isIDError: AddDispenserIDErrors = AddDispenserIDErrors.NONE,
    val isNameError: AddDispenserNameErrors = AddDispenserNameErrors.NONE,
    val allDispenserNames: List<String> = emptyList(),
    val allDispenserIds: List<String> = emptyList(),
    //val isCountError: Boolean = false,
)