package com.example.aurora.features.dispenser

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.aurora.R
import com.example.aurora.features.profile.LogoutPopup
import com.example.aurora.ui.theme.FontSize
import com.example.aurora.ui.theme.FontWeight
import com.example.aurora.ui.theme.LineHeight
import com.example.aurora.ui.theme.base0
import com.example.aurora.ui.theme.baseBlue
import com.example.aurora.ui.theme.primary1
import com.example.aurora.ui.theme.secondary4

@Composable
fun ScheduleScreen(
    schedule: ScheduleDetailData,
    onBackClick: () -> Unit,
    onEditToggle: () -> Unit,
    onDeleteClick: () -> Unit,
    showHideDelete: Boolean,
    onBackToScheduleDeleteClicked: () -> Unit,
    onDayChange: (Int) -> Unit,
    onHourChange: (Int) -> Unit,
    onMinuteChange: (Int) -> Unit,
    onRepeatChange: (Boolean) -> Unit,
    onSave: () -> Unit,
) {
    LaunchedEffect(schedule.isSuccess, schedule.isDeleted) {
        if (schedule.isSuccess || schedule.isDeleted) onBackClick()
    }

    Scaffold{ innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp, 16.dp),
            verticalArrangement = Arrangement.spacedBy(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Back(onBackClick)
            Spacer(modifier = Modifier.weight(0.1f))
            Text(
                text = "Schedule:",
                color = primary1,
                fontSize = FontSize.HEADING3,
                fontWeight = FontWeight.HEADING3,
                lineHeight = LineHeight.HEADING3
            )

            ScheduleHeaderCard(schedule, onEditToggle, onBackToScheduleDeleteClicked)

            FormCard(
                schedule = schedule,
                onDayChange = onDayChange,
                onHourChange = onHourChange,
                onMinuteChange = onMinuteChange,
                onRepeatChange = onRepeatChange
            )

            if (!schedule.errorMessage.isNullOrEmpty()) {
                Text(
                    text = schedule.errorMessage,
                    color = Color.Red,
                    fontSize = FontSize.PARAGRAPH2,
                    fontWeight = FontWeight.PARAGRAPH2M
                )
            }

            Spacer(modifier = Modifier.weight(0.1f))

            Button(
                onClick = { onSave() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(8.dp),
                enabled = schedule.isEditing && !schedule.isSaving,
                colors = ButtonDefaults.buttonColors(containerColor = primary1)

            ) {
                Text(
                    text = if (schedule.isLoading) "Saving...  " else "Save  ",
                    fontSize = FontSize.PARAGRAPH1,
                    fontWeight = FontWeight.PARAGRAPH1M,
                    lineHeight = LineHeight.PARAGRAPH1,
                    color = base0
                )
                Image(
                    painter = painterResource(id = R.drawable.add_circle),
                    contentDescription = "add",
                    modifier = Modifier
                        .size(20.dp)
                )
            }
            Spacer(modifier = Modifier.weight(0.1f))
        }
    }
    if (showHideDelete) {
        LogoutPopup(
            onDismiss = { onBackToScheduleDeleteClicked() },
            onConfirmLogout = { onDeleteClick() },
            title = "Delete schedule",
            caption = "Are you sure you want to delete this schedule?",
            buttonText = "Delete"
        )
    }
}

@Composable
private fun ScheduleHeaderCard(
    schedule: ScheduleDetailData,
    onEditToggle: () -> Unit,
    onDelete: () -> Unit
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
                .padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = formatDay(schedule.dayOfWeek),
                        color = primary1,
                        fontSize = FontSize.BODY2,
                        fontWeight = FontWeight.BODY2,
                        lineHeight = LineHeight.BODY2
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = formatTime(schedule.hour, schedule.minute),
                        color = secondary4,
                        fontSize = FontSize.PARAGRAPH2,
                        fontWeight = FontWeight.PARAGRAPH2M
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = schedule.containerName,
                    fontSize = FontSize.PARAGRAPH1,
                    fontWeight = FontWeight.PARAGRAPH1M,
                    lineHeight = LineHeight.PARAGRAPH1,
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
                    R.drawable.pen,
                    if (schedule.isEditing) "Cancel edit" else "Edit", onEditToggle
                )
                ActionChip(
                    R.drawable.delete,
                    "Delete",
                    onDelete,
                    isDestructive = true
                )
            }
        }
    }
}

@Composable
private fun FormCard(
    schedule: ScheduleDetailData,
    onDayChange: (Int) -> Unit,
    onHourChange: (Int) -> Unit,
    onMinuteChange: (Int) -> Unit,
    onRepeatChange: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        DayDropdown(
            selected = schedule.dayOfWeek,
            onSelected = onDayChange,
            enabled = schedule.isEditing
        )
        Column(
            horizontalAlignment = Alignment.Start
        ){
            TimePickerField(
                hour = schedule.hour,
                minute = schedule.minute,
                enabled = schedule.isEditing,
                onTimeSelected = { h, m ->
                    onHourChange(h)
                    onMinuteChange(m)
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "*Times are in 24h!",
                color = secondary4,
                fontSize = FontSize.PARAGRAPH2,
                fontWeight = FontWeight.PARAGRAPH2M
            )
        }

        Switch(
            repeat = schedule.repeat,
            onRepeatChange = onRepeatChange,
            enabled = schedule.isEditing
        )
    }
}

private fun formatDay(day: Int): String =
    when (day) {
        0 -> "Monday"
        1 -> "Tuesday"
        2 -> "Wednesday"
        3 -> "Thursday"
        4 -> "Friday"
        5 -> "Saturday"
        6 -> "Sunday"
        else -> "Day not set"
    }

private fun formatTime(hour: Int, minute: Int): String {
    if (hour !in 0..23 || minute !in 0..59) return "--:--"
    return String.format("%02d:%02d", hour, minute)
}

