package com.example.aurora.features.admin

import com.example.aurora.data.model.AdminDispenserModel
import com.example.aurora.data.model.AdminDispenserModelModel

enum class AdminHomeTab { DISPENSERS, MODELS, USERS }

data class AdminHomeData(
    val name: String = "",
    val activeTab: AdminHomeTab = AdminHomeTab.DISPENSERS,
    val dispensers: List<AdminDispenserModel> = emptyList(),
    val models: List<AdminDispenserModelModel> = emptyList(),
    val users: List<com.example.aurora.data.model.AdminUserModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
