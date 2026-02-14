package com.example.aurora.features.dispenser

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimeInput
import com.example.aurora.R
import com.example.aurora.ui.theme.FontSize
import com.example.aurora.ui.theme.FontWeight
import com.example.aurora.ui.theme.LineHeight
import com.example.aurora.ui.theme.baseBlue
import com.example.aurora.ui.theme.baseLightBlue
import com.example.aurora.ui.theme.baseTransparentBlue
import com.example.aurora.ui.theme.primary1
import com.example.aurora.ui.theme.secondary2
import com.example.aurora.ui.theme.secondary4
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import com.example.aurora.ui.theme.base0
import com.example.aurora.ui.theme.secondary6
import com.example.aurora.ui.components.ErrorBanner
import org.koin.dsl.module

@Composable
fun AddScheduleScreen(
    schedule: ScheduleFormState,
    onDayChange: (Int) -> Unit,
    onHourChange: (Int) -> Unit,
    onMinuteChange: (Int) -> Unit,
    onRepeatChange: (Boolean) -> Unit,
    onSave: () -> Unit,
    onBackClick: () -> Unit
) {
    LaunchedEffect(schedule.isSuccess) {
        if (schedule.isSuccess) onBackClick()
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(vertical = 16.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Back(onBackClick)
            Spacer(modifier = Modifier.weight(0.1f))
            Text(
                text = "Add schedule:",
                color = primary1,
                fontSize = FontSize.HEADING3,
                fontWeight = FontWeight.HEADING3,
                lineHeight = LineHeight.HEADING3
            )
            Spacer(modifier = Modifier.weight(0.2f))
            DayDropdown(
                selected = schedule.dayOfWeek,
                onSelected = onDayChange,
                enabled = true
            )
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                TimePickerField(
                    hour = schedule.hour,
                    minute = schedule.minute,
                    onTimeSelected = { h, m ->
                        onHourChange(h)
                        onMinuteChange(m)
                    },
                    enabled = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "*Times are in 24h!",
                    color = secondary4,
                    fontSize = FontSize.PARAGRAPH2,
                    fontWeight = FontWeight.PARAGRAPH2M,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }


            Switch(
                repeat = schedule.repeat,
                onRepeatChange = onRepeatChange,
                enabled = true
            )

            ErrorBanner(schedule.errorMessage)

            Spacer(modifier = Modifier.weight(0.3f))

            Button(
                onClick = { onSave() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(8.dp),
                enabled = schedule.isValid &&
                        (schedule.dayOfWeek in 0..6) &&
                        (schedule.hour in 0..23) &&
                        (schedule.minute in 0..59) &&
                        !schedule.isLoading,
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayDropdown(
    selected: Int,
    onSelected: (Int) -> Unit,
    enabled: Boolean
) {
    var expanded by remember { mutableStateOf(false) }
    val baseDays = listOf(
        "--Select day--" to -1,
        "Monday" to 0,
        "Tuesday" to 1,
        "Wednesday" to 2,
        "Thursday" to 3,
        "Friday" to 4,
        "Saturday" to 5,
        "Sunday" to 6,
    )
    val days = if (selected == -1) baseDays else baseDays.filterNot { it.second == -1 }
    val label = days.firstOrNull { it.second == selected }?.first ?: "--Select day--"

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { if(enabled) expanded = !expanded }
    ) {
        OutlinedTextField(
            value = label,
            onValueChange = {},
            readOnly = true,
            enabled = enabled,
            label = { Text("Day of week") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = secondary2,
                unfocusedBorderColor = secondary2,
                focusedLabelColor = secondary2
            )
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(baseBlue)
        ) {
            days.forEachIndexed { index, (title, value) ->

                DropdownMenuItem(
                    text = { Text(title) },
                    onClick = {
                        onSelected(value)
                        expanded = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(baseBlue),
                    colors = MenuDefaults.itemColors(
                        textColor = secondary6
                    ),
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )

                if (index < days.lastIndex) {
                    Icon(
                        painter = painterResource(id = R.drawable.divider_horizontal),
                        contentDescription = "divider"
                    )
                }
            }
        }
    }
}

@Composable
private fun AuroraTimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    showDial: Boolean,
    onToggleMode: () -> Unit,
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        titleContentColor = primary1,
        textContentColor = primary1,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ){
                Text("Select time",
                    color = primary1,
                    fontSize = FontSize.BODY1,
                    fontWeight = FontWeight.BODY1,
                    lineHeight = LineHeight.BODY1
                )
            }

        },
        text = { content() },

        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onToggleMode) {
                    Icon(
                        imageVector = if (showDial) Icons.Filled.Keyboard else Icons.Filled.AccessTime,
                        contentDescription = if (showDial) "Switch to input" else "Switch to dial",
                        tint = secondary2
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextButton(
                        onClick = onDismiss,
                        colors = ButtonDefaults.textButtonColors(contentColor = secondary4)
                    ) { Text("Cancel") }

                    TextButton(
                        onClick = onConfirm,
                        colors = ButtonDefaults.textButtonColors(contentColor = secondary2)
                    ) { Text("OK") }
                }
            }
        },

        dismissButton = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerField(
    hour: Int,
    minute: Int,
    onTimeSelected: (Int, Int) -> Unit,
    enabled: Boolean
) {
    var showDialog by remember { mutableStateOf(false) }
    var showDial by remember { mutableStateOf(true) }

    val pickerState = rememberTimePickerState(
        initialHour = hour.coerceIn(0, 23),
        initialMinute = minute.coerceIn(0, 59),
        is24Hour = true
    )

    val pickerColors = TimePickerDefaults.colors(
        containerColor = Color.White,
        clockDialColor = baseTransparentBlue,
        selectorColor = secondary2,
        timeSelectorSelectedContainerColor = secondary2,
        timeSelectorSelectedContentColor = Color.White,
        timeSelectorUnselectedContentColor = secondary4,
        timeSelectorUnselectedContainerColor = baseTransparentBlue
    )

    if (showDialog) {
        AuroraTimePickerDialog(
            onDismiss = { showDialog = false },
            onConfirm = {
                onTimeSelected(pickerState.hour, pickerState.minute)
                showDialog = false
            },
            showDial = showDial,
            onToggleMode = { showDial = !showDial }
        ) {
            if (showDial) {
                Box(
                    contentAlignment = Alignment.Center
                ){
                    TimePicker(state = pickerState, colors = pickerColors)
                }
            } else {
                Box(
                    contentAlignment = Alignment.Center
                ){
                    TimeInput(state = pickerState, colors = pickerColors)
                }
            }
        }
    }

    val hasTime = hour in 0..23 && minute in 0..59
    val display = if (hasTime) String.format("%02d:%02d", hour, minute) else "--:--"

    OutlinedTextField(
        value = display,
        onValueChange = {},
        readOnly = true,
        enabled = enabled,
        label = { Text("Time (24h)") },
        trailingIcon = {
            IconButton(onClick = { if(enabled) showDialog = true }) {
                Icon(
                    painter = painterResource(id = R.drawable.pen),
                    contentDescription = "Pick time"
                )
            }
        },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = secondary2,
            unfocusedBorderColor = secondary2,
            focusedLabelColor = secondary2
        )
    )
}

@Composable
fun Switch(
    repeat: Boolean,
    onRepeatChange: (Boolean) -> Unit,
    enabled: Boolean
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Repeat weekly",
                color = if(enabled) primary1 else secondary4,
                fontSize = FontSize.PARAGRAPH1,
                fontWeight = FontWeight.PARAGRAPH1M
            )
            Text(
                text = "Runs every week on selected day",
                color = secondary4,
                fontSize = FontSize.PARAGRAPH2,
                fontWeight = FontWeight.PARAGRAPH2M
            )
        }
        Switch(
            checked = repeat,
            onCheckedChange = onRepeatChange,
            enabled = enabled,
            colors = SwitchDefaults.colors(
                checkedTrackColor = baseLightBlue,
                uncheckedTrackColor = baseTransparentBlue,
                checkedThumbColor = secondary2,
                uncheckedThumbColor = secondary2,

                disabledCheckedTrackColor = baseLightBlue.copy(alpha = 0.3f),
                disabledUncheckedTrackColor = baseTransparentBlue.copy(alpha = 0.3f),
                disabledCheckedThumbColor = secondary2.copy(alpha = 0.3f),
                disabledUncheckedThumbColor = secondary2.copy(alpha = 0.3f),
            )
        )
    }
}