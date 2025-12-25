package com.example.aurora.features.home

import com.example.aurora.data.model.ContainerModel
import com.example.aurora.data.model.UserModel

data class DispenserData (
    val id: String = "",
    val name: String = "",
    //val owner: UserModel,
    //val containers: ContainerModel,
    val isAddDispenserSuccessful: Boolean = false,
    val isIDError: AddDispenserIDErrors = AddDispenserIDErrors.NONE,
    val isNameError: AddDispenserNameErrors = AddDispenserNameErrors.NONE,
)