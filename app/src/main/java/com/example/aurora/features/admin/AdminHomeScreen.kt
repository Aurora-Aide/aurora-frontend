package com.example.aurora.features.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.aurora.R
import com.example.aurora.features.home.BottomNavigationBar
import com.example.aurora.ui.components.ErrorBanner
import com.example.aurora.ui.theme.FontSize
import com.example.aurora.ui.theme.FontWeight
import com.example.aurora.ui.theme.LineHeight
import com.example.aurora.ui.theme.base10
import com.example.aurora.ui.theme.baseLightBlue
import com.example.aurora.ui.theme.primary1
import com.example.aurora.ui.theme.secondary2
import com.example.aurora.ui.theme.secondary4

@Composable
fun AdminHomeScreen(
    data: AdminHomeData,
    onTabChange: (AdminHomeTab) -> Unit,
    onReloadDispensers: () -> Unit,
    onReloadModels: () -> Unit,
    onReloadUsers: () -> Unit,
    onAddModelClick: () -> Unit,
    onToProfileClick: () -> Unit,
    onDispenserClick: (String, String) -> Unit
) {
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        if (data.activeTab == AdminHomeTab.DISPENSERS && data.dispensers.isEmpty()) {
            onReloadDispensers()
        } else if (data.activeTab == AdminHomeTab.MODELS && data.models.isEmpty()) {
            onReloadModels()
        } else if (data.activeTab == AdminHomeTab.USERS && data.users.isEmpty()) {
            onReloadUsers()
        }
    }

    Scaffold(
        floatingActionButton = {
            if (data.activeTab == AdminHomeTab.MODELS) {
                FloatingActionButton(
                    modifier = Modifier.size(68.dp),
                    onClick = { onAddModelClick() },
                    containerColor = baseLightBlue,
                    contentColor = secondary2,
                    elevation = FloatingActionButtonDefaults.elevation(4.dp),
                    shape = CircleShape,
                ) {
                    Icon(Icons.Filled.Add, "Add Dispenser")
                }
            }
        },
        bottomBar = {
            BottomAppBar(
                containerColor = base10,
                modifier = Modifier.height(64.dp)
            ) {
                BottomNavigationBar(onToProfileClick = onToProfileClick)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.hello_name, data.name),
                color = primary1,
                fontSize = FontSize.BODY1,
                fontWeight = FontWeight.BODY1,
                lineHeight = LineHeight.BODY1,
            )
            Spacer(modifier = Modifier.height(20.dp))

            val selectedIndex = when (data.activeTab) {
                AdminHomeTab.DISPENSERS -> 0
                AdminHomeTab.MODELS -> 1
                AdminHomeTab.USERS -> 2
            }

            TabRow(
                selectedTabIndex = selectedIndex,
                indicator = { tabPositions ->
                    TabRowDefaults.PrimaryIndicator(
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[selectedIndex]),
                        color = primary1,
                        width = tabPositions[selectedIndex].width
                    )
                }
            ) {
                Tab(
                    selected = data.activeTab == AdminHomeTab.DISPENSERS,
                    onClick = { onTabChange(AdminHomeTab.DISPENSERS) },
                    text = { Text(stringResource(R.string.dispensers)) },
                    selectedContentColor = primary1,
                    unselectedContentColor = primary1,
                )
                Tab(
                    selected = data.activeTab == AdminHomeTab.MODELS,
                    onClick = { onTabChange(AdminHomeTab.MODELS) },
                    text = { Text(stringResource(R.string.dispenser_models)) },
                    selectedContentColor = primary1,
                    unselectedContentColor = primary1,
                )
                Tab(
                    selected = data.activeTab == AdminHomeTab.USERS,
                    onClick = { onTabChange(AdminHomeTab.USERS) },
                    text = { Text(stringResource(R.string.users)) },
                    selectedContentColor = primary1,
                    unselectedContentColor = primary1,
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (data.errorMessage != com.example.aurora.ui.UiMessage.NONE) {
                ErrorBanner(data.errorMessage)
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        when (data.activeTab) {
                            AdminHomeTab.DISPENSERS -> onReloadDispensers()
                            AdminHomeTab.MODELS -> onReloadModels()
                            AdminHomeTab.USERS -> onReloadUsers()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = primary1)
                ) {
                    Text(stringResource(R.string.retry), color = secondary2)
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                when (data.activeTab) {
                    AdminHomeTab.DISPENSERS -> {
                    if (data.dispensers.isEmpty() && !data.isLoading) {
                        Text(
                            text = stringResource(R.string.no_dispensers_found),
                            color = secondary4,
                            fontSize = FontSize.PARAGRAPH2,
                            fontWeight = FontWeight.PARAGRAPH2R,
                            lineHeight = LineHeight.PARAGRAPH2,
                        )
                    }
                    data.dispensers.forEach { dispenser ->
                        DispenserBox(
                            name = dispenser.name,
                            id = dispenser.id.toString(),
                            serialId = dispenser.serial_id,
                            owner = dispenser.owner?.email,
                            size = dispenser.size,
                            model = dispenser.model?.name,
                            onClick = { onDispenserClick(dispenser.id.toString(), dispenser.name) }
                        )
                    }
                }
                    AdminHomeTab.MODELS -> {
                        if (data.models.isEmpty() && !data.isLoading) {
                            Text(
                                text = stringResource(R.string.no_models_found),
                                color = secondary4,
                                fontSize = FontSize.PARAGRAPH2,
                                fontWeight = FontWeight.PARAGRAPH2R,
                                lineHeight = LineHeight.PARAGRAPH2,
                            )
                        }
                        data.models.forEach { model ->
                            AdminModelCard(model)
                        }
                    }
                    AdminHomeTab.USERS -> {
                        if (data.users.isEmpty() && !data.isLoading) {
                            Text(
                                text = stringResource(R.string.no_users_found),
                                color = secondary4,
                                fontSize = FontSize.PARAGRAPH2,
                                fontWeight = FontWeight.PARAGRAPH2R,
                                lineHeight = LineHeight.PARAGRAPH2,
                            )
                        }
                        data.users.forEach { user ->
                            AdminUserCard(user)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AdminModelCard(model: com.example.aurora.data.model.AdminDispenserModelModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Text(
            text = model.name,
            color = primary1,
            fontSize = FontSize.BODY2,
            fontWeight = FontWeight.BODY2
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(
                R.string.admin_model_summary,
                model.code,
                model.slot_count,
                model.serial_prefix
            ),
            color = secondary4,
            fontSize = FontSize.PARAGRAPH2,
            fontWeight = FontWeight.PARAGRAPH2R,
            lineHeight = LineHeight.PARAGRAPH2,
        )
    }
}

@Composable
private fun AdminUserCard(user: com.example.aurora.data.model.AdminUserModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Text(
            text = user.email,
            color = primary1,
            fontSize = FontSize.BODY2,
            fontWeight = FontWeight.BODY2
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(
                R.string.admin_user_summary,
                user.first_name,
                user.last_name,
                stringResource(if (user.is_staff) R.string.staff else R.string.user)
            ),
            color = secondary4,
            fontSize = FontSize.PARAGRAPH2,
            fontWeight = FontWeight.PARAGRAPH2R,
            lineHeight = LineHeight.PARAGRAPH2,
        )
    }
}
