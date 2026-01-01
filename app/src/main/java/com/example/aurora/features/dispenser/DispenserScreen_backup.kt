package com.example.aurora.features.dispenser

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.aurora.R
import com.example.aurora.features.profile.LogoutPopup
import com.example.aurora.ui.theme.FontSize
import com.example.aurora.ui.theme.FontWeight
import com.example.aurora.ui.theme.LineHeight
import com.example.aurora.ui.theme.functionalError
import com.example.aurora.ui.theme.primary1
import com.example.aurora.ui.theme.secondary4

@Composable
fun DispenserScreen(
    viewModel: DispenserViewModel,
    name: String,
    id: String,
    onPillClick: () -> Unit,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
)
{
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showDeleteConfirm by remember { mutableStateOf(false) }

    androidx.compose.runtime.LaunchedEffect(name, id) {
        viewModel.loadDispenser(id, name)
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
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            HeaderCard(
                uiState = uiState,
                onEditClick = onEditClick,
                onDeleteClick = { showDeleteConfirm = true },
                actionsEnabled = !uiState.isDeleting
            )

            Spacer(modifier = Modifier.height(16.dp))

            ContentArea(
                uiState = uiState,
                onPillClick = onPillClick
            )
        }
    }

    if (showDeleteConfirm) {
        LogoutPopup(
            onDismiss = { showDeleteConfirm = false },
            onConfirmLogout = {
                showDeleteConfirm = false
                onDeleteClick()
            },
            title = "Delete dispenser",
            caption = "Are you sure you want to delete this dispenser?",
            buttonText = "Delete"
        )
    }
}

@Composable
fun HeaderCard(
    uiState: DispenserUiState,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    actionsEnabled: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFD)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
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
                // StatusBadge(status = uiState.status) // Uncomment when backend provides status
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ActionChip(
                    iconRes = R.drawable.pen,
                    label = "Edit",
                    onClick = onEditClick,
                    enabled = actionsEnabled
                )
                ActionChip(
                    iconRes = R.drawable.delete,
                    label = if (actionsEnabled) "Delete" else "Deleting...",
                    onClick = onDeleteClick,
                    enabled = actionsEnabled,
                    isDestructive = true
                )
            }
        }
    }
}

@Composable
fun ContentArea(
    uiState: DispenserUiState,
    onPillClick: () -> Unit
) {
    when {
        uiState.isLoading -> {
            LoadingPlaceholderList()
        }

        uiState.errorMessage != null -> {
            ErrorState(message = uiState.errorMessage)
        }

        uiState.containers.isEmpty() -> {
            EmptyState()
        }

        else -> {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.containers) { container ->
                    ContainerRow(
                        title = container.title,
                        subtitle = container.subtitle,
                        onClick = onPillClick
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
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
                    .background(Color(0xFFE8EEF5))
            )
        }
    }
}

@Composable
fun EmptyState() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F6FA))
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
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF1F0)),
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
            .padding(vertical = 8.dp),
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
    enabled: Boolean,
    isDestructive: Boolean = false
) {
    val background = if (isDestructive) Color(0xFFFFF4F2) else Color(0xFFEAF2FF)
    val contentColor = if (isDestructive) functionalError else primary1
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(background)
            .clickable(enabled = enabled) { onClick() }
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

