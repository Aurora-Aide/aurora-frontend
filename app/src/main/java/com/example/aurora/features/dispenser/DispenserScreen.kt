package com.example.aurora.features.dispenser

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.aurora.R
import com.example.aurora.features.home.AddDispenserNameErrors
import com.example.aurora.features.login.TextField
import com.example.aurora.features.profile.LogoutPopup
import com.example.aurora.ui.theme.FontSize
import com.example.aurora.ui.theme.FontWeight
import com.example.aurora.ui.theme.LineHeight
import com.example.aurora.ui.theme.base10
import com.example.aurora.ui.theme.base100
import com.example.aurora.ui.theme.baseBlue
import com.example.aurora.ui.theme.baseError
import com.example.aurora.ui.theme.baseLightBlue
import com.example.aurora.ui.theme.functionalError
import com.example.aurora.ui.theme.primary1
import com.example.aurora.ui.theme.secondary2
import com.example.aurora.ui.theme.secondary4

@Composable
fun DispenserScreen(
    dispenser: DispenserData,
    showHideRename: Boolean,
    showHideDelete: Boolean,
    onPillClick: (slotNumber: Int, pillName: String, containerId: Int) -> Unit,
    onBackClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onRenameChange: (String) -> Unit,
    onRenameConfirm: () -> Unit,
    isRenameSuccessful: () -> Unit,
    onBackToDispenserRenameClicked: () -> Unit,
    onBackToDispenserDeleteClicked: () -> Unit,

)
{
    if (dispenser.isRenameSuccessful) {
        LaunchedEffect(null) {
            isRenameSuccessful()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            DispenserTopBar(onBackClick = onBackClick)
        }
    ) { innerPadding ->
    Column(
        modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            DispenserHeaderCard(
                uiState = dispenser,
                onEditClick = { onBackToDispenserRenameClicked() },
                onDeleteClick = { onBackToDispenserDeleteClicked() },
            )

            Spacer(modifier = Modifier.height(16.dp))

            ContentArea(
                uiState = dispenser,
                onPillClick = onPillClick
            )
        }
    }

    if (showHideDelete) {
        LogoutPopup(
            onDismiss = { onBackToDispenserDeleteClicked() },
            onConfirmLogout = { onDeleteClick() },
            title = "Delete dispenser",
            caption = "Are you sure you want to delete this dispenser?",
            buttonText = "Delete"
        )
    }

    if (showHideRename) {
        DispenserPopup(
            onDismiss = { onBackToDispenserRenameClicked() },
            onConfirmEdit = { onRenameConfirm() },
            title = "Rename Dispenser:",
            caption = "Please type the new name for your dispenser:",
            onNameChange = { onRenameChange(it) },
            value = dispenser.renameDraft,
            placeHolder = dispenser.name,
            error = dispenser.isRenameError
        )
    }
}

@Composable
fun DispenserHeaderCard(
    uiState: DispenserData,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = baseBlue,
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 0.dp,
        shadowElevation = 2.dp,
    ) {
    Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
    ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
            Text(
                        text = uiState.name,
                fontSize = FontSize.HEADING3,
                fontWeight = FontWeight.HEADING3,
                lineHeight = LineHeight.HEADING3,
                color = primary1
            )
                    Spacer(modifier = Modifier.height(6.dp))
        Text(
                        text = "ID: ${uiState.id}",
            fontSize = FontSize.PARAGRAPH2,
            fontWeight = FontWeight.PARAGRAPH2M,
            lineHeight = LineHeight.PARAGRAPH2,
            color = primary1
        )
    }
}

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ActionChip(
                    iconRes = R.drawable.pen,
                    label = "Rename",
                    onClick = onEditClick,
                )
                ActionChip(
                    iconRes = R.drawable.delete,
                    onClick = onDeleteClick,
                    isDestructive = true,
                    label = "Delete"
                )
            }
        }
    }
}

@Composable
fun ContentArea(
    uiState: DispenserData,
    onPillClick: (slotNumber: Int, pillName: String, containerId: Int) -> Unit
) {
    when {
        uiState.errorMessage != null -> {
            ErrorState(message = uiState.errorMessage)
        }

        uiState.containers.isEmpty() -> {
            EmptyState()
        }

        else -> {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues( bottom = 32.dp)
            ) {
                items(uiState.containers) { container ->
                    ContainerRow(
                        title = container.title,
                        subtitle = container.subtitle,
                        onClick = { onPillClick(container.slotNumber, container.title, container.containerId) }
                    )
                }
            }
        }
    }
}

@Composable
fun ContainerRow(
    title: String,
    subtitle: String?,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        color = baseLightBlue,
        tonalElevation = 0.dp,
        shadowElevation = 2.dp,
    ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
                .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
            Column(modifier = Modifier.weight(1f)) {
        Text(
                    text = title,
            color = primary1,
            fontSize = FontSize.PARAGRAPH1,
            fontWeight = FontWeight.PARAGRAPH1M
        )
                subtitle?.let {
                    Spacer(modifier = Modifier.height(4.dp))
        Text(
                        text = it,
            color = secondary4,
                        fontSize = FontSize.PARAGRAPH2,
                        fontWeight = FontWeight.PARAGRAPH2M
        )
                }
            }
        Image(
            painter = painterResource(R.drawable.angl_left),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
    }
    }
}

@Composable
fun LoadingPlaceholderList() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        repeat(3) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(base10)
            )
        }
    }
}

@Composable
fun EmptyState() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = baseLightBlue)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "No containers yet",
                color = primary1,
                fontSize = FontSize.PARAGRAPH1,
                fontWeight = FontWeight.PARAGRAPH1M
            )
            Text(
                text = "Add containers to manage schedules and pills for this dispenser.",
                color = secondary4,
                fontSize = FontSize.PARAGRAPH2,
                fontWeight = FontWeight.PARAGRAPH2M
            )
        }
    }
}

@Composable
fun ErrorState(message: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = baseBlue),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Something went wrong",
                color = functionalError,
                fontSize = FontSize.PARAGRAPH1,
                fontWeight = FontWeight.PARAGRAPH1M
            )
            Text(
                text = message,
                color = functionalError,
                fontSize = FontSize.PARAGRAPH2,
                fontWeight = FontWeight.PARAGRAPH2M
            )
        }
    }
}

@Composable
fun DispenserTopBar(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            painter = painterResource(id = R.drawable.backarrow),
            contentDescription = "back",
            modifier = Modifier
                .size(28.dp)
                .clickable { onBackClick() }
        )
    }
}

@Composable
fun ActionChip(
    iconRes: Int,
    label: String,
    onClick: () -> Unit,
    isDestructive: Boolean = false
) {
    val background = if (isDestructive) baseError else baseLightBlue
    val contentColor = if (isDestructive) functionalError else primary1
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(background)
            .clickable{ onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            modifier = Modifier.size(18.dp)
        )
        Text(
            text = label,
            color = contentColor,
            fontSize = FontSize.PARAGRAPH2,
            fontWeight = FontWeight.PARAGRAPH2M
        )
    }
}

@Composable
fun DispenserPopup(
    onDismiss: () -> Unit,
    onConfirmEdit: () -> Unit,
    title: String,
    caption: String,
    onNameChange: (String) -> Unit,
    value: String,
    placeHolder: String,
    error: AddDispenserNameErrors
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(base100.copy(0.3f))
            .clickable { onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        Popup(
            alignment = Alignment.Center,
            onDismissRequest = onDismiss,
            properties = PopupProperties(focusable = true)
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .padding(24.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = title,
                        color = primary1,
                        fontSize = FontSize.BODY2,
                        fontWeight = FontWeight.BODY2,
                        lineHeight = LineHeight.BODY2,
                    )

                    Text(
                        text = caption,
                        color = secondary2,
                        fontSize = FontSize.PARAGRAPH2,
                        fontWeight = FontWeight.PARAGRAPH2R,
                        lineHeight = LineHeight.PARAGRAPH2,
                    )

                    TextField(
                        value = value,
                        onValueChange = onNameChange,
                        label = "Name",
                        placeholder = placeHolder,
                        errorText = error.value?.let { stringResource(it) } ?: "",
                        error = error != AddDispenserNameErrors.NONE,
                        trailingIcon = {
                            IconButton(
                                onClick = {}
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.pen),
                                    contentDescription = "pen",
                                )
                            }
                        }
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .border(
                                    width = 1.dp,
                                    color = primary1,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .clickable { onDismiss() }
                                .padding(vertical = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(R.string.back),
                                color = primary1,
                                fontSize = FontSize.PARAGRAPH2,
                                fontWeight = FontWeight.PARAGRAPH2M,
                                lineHeight = LineHeight.PARAGRAPH2,
                            )
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .border(
                                    width = 1.dp,
                                    color = primary1,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .clickable { onConfirmEdit() }
                                .padding(vertical = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Rename",
                                color = primary1,
                                fontSize = FontSize.PARAGRAPH2,
                                fontWeight = FontWeight.PARAGRAPH2M,
                                lineHeight = LineHeight.PARAGRAPH2,
                            )
                        }
                    }
                }
            }
        }
    }
}