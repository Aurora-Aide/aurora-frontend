package com.example.aurora.features.dispenser

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.aurora.R
import com.example.aurora.ui.components.ErrorBanner
import com.example.aurora.ui.theme.FontSize
import com.example.aurora.ui.theme.FontWeight
import com.example.aurora.ui.theme.LineHeight
import com.example.aurora.ui.theme.baseBlue
import com.example.aurora.ui.theme.baseLightBlue
import com.example.aurora.ui.theme.primary1
import com.example.aurora.ui.theme.secondary2
import com.example.aurora.ui.theme.secondary4

@Composable
fun ContainerScreen(
    container: ContainerData,
    showHideRename: Boolean,
    onAddScheduleClick: () -> Unit,
    onDropNowClick: () -> Unit,
    onScheduleRowClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    onRenameChange: (String) -> Unit,
    onRenameConfirm: () -> Unit,
    isRenameSuccessful: () -> Unit,
    onBackToContainerRenameClicked: () -> Unit,
)
{
    val scrollState = rememberScrollState()

    if (container.isRenameSuccessful) {
        LaunchedEffect(null) {
            isRenameSuccessful()
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.size(68.dp),
                onClick = { onAddScheduleClick() },
                containerColor = baseLightBlue,
                contentColor = secondary2,
                elevation = FloatingActionButtonDefaults.elevation(4.dp),
                shape = CircleShape,
            ) {
                Icon(Icons.Filled.Add, "Add Schedule")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 16.dp)
                    .verticalScroll(scrollState)
            ) {
                Back(onBackClick)
                Spacer(modifier = Modifier.height(16.dp))
                ContainerHeaderCard(
                    container = container,
                    onDropNowClick = onDropNowClick,
                    onEditClick = { onBackToContainerRenameClicked() }
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(R.string.schedules),
                    color = primary1,
                    fontSize = FontSize.BODY1,
                    fontWeight = FontWeight.BODY1,
                    lineHeight = LineHeight.BODY1,
                    modifier = Modifier.padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                ErrorBanner(container.errorMessage)
                ScheduleList(container, onScheduleRowClick)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    if (showHideRename) {
        DispenserPopup(
            onDismiss = { onBackToContainerRenameClicked() },
            onConfirmEdit = { onRenameConfirm() },
            title = stringResource(R.string.rename_pill_title),
            caption = stringResource(R.string.rename_pill_caption),
            onNameChange = { onRenameChange(it) },
            value = container.renameDraft,
            placeHolder = container.pillName,
            error = container.isRenameError,
        )
    }
}

@Composable
fun ScheduleList(
    container: ContainerData,
    onClick: (Int) -> Unit
) {
    if (container.schedules.isEmpty()) {
        Text(
            text = stringResource(R.string.no_schedules_yet),
            color = secondary4,
            fontSize = FontSize.PARAGRAPH2,
            fontWeight = FontWeight.PARAGRAPH2M,
            lineHeight = LineHeight.PARAGRAPH2,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    } else {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            container.schedules.forEach { schedule ->
                ScheduleRow(schedule) { onClick(schedule.id) }
            }
        }
    }
}

@Composable
fun ScheduleRow(
    schedule: ScheduleData,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = schedule.day.value?.let { stringResource(it) } ?: stringResource(R.string.day),
                    color = primary1,
                    fontSize = FontSize.PARAGRAPH1,
                    fontWeight = FontWeight.PARAGRAPH1M
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = formatTime(schedule.hour, schedule.minutes),
                    color = secondary4,
                    fontSize = FontSize.PARAGRAPH2,
                    fontWeight = FontWeight.PARAGRAPH2M
                )
            }
            Text(
                text = if (schedule.repeating) {
                    stringResource(R.string.repeats)
                } else {
                    stringResource(R.string.once)
                },
                color = primary1,
                fontSize = FontSize.PARAGRAPH2,
                fontWeight = FontWeight.PARAGRAPH2M
            )
            Spacer(modifier = Modifier.weight(0.2f))
            Image(
                painter = painterResource(R.drawable.angl_left),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

private fun formatTime(hour: Int, minute: Int): String {
    val h = hour.coerceIn(0,23)
    val m = minute.coerceIn(0,59)
    return String.format("%02d:%02d", h, m)
}

@Composable
fun ContainerHeaderCard(
    container: ContainerData,
    onDropNowClick: () -> Unit,
    onEditClick: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
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
                        text = container.pillName,
                        fontSize = FontSize.HEADING3,
                        fontWeight = FontWeight.HEADING3,
                        lineHeight = LineHeight.HEADING3,
                        color = primary1
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.slot_value, container.slotNumber),
                        fontSize = FontSize.PARAGRAPH2,
                        fontWeight = FontWeight.PARAGRAPH2M,
                        lineHeight = LineHeight.PARAGRAPH2,
                        color = primary1
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = container.dispenserName,
                    fontSize = FontSize.BODY2,
                    fontWeight = FontWeight.BODY2,
                    lineHeight = LineHeight.BODY2,
                    color = primary1,
                    modifier = Modifier.align(alignment = Alignment.Top)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ActionChip(
                    iconRes = R.drawable.download,
                    label = stringResource(R.string.drop_now),
                    onClick = onDropNowClick,
                )
                ActionChip(
                    iconRes = R.drawable.pen,
                    label = stringResource(R.string.rename),
                    onClick = onEditClick,
                )
            }
        }
    }
}

@Composable
fun Back(
    onBackClick:() -> Unit,
){
    Row{
        Image(
            painter = painterResource(id = R.drawable.backarrow),
            contentDescription = "back",
            modifier = Modifier
                .size(28.dp)
                .clickable { onBackClick() }
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}