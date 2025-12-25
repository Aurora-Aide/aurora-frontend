package com.example.aurora.features.dispenser

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.unit.dp
import com.example.aurora.ui.theme.FontSize
import com.example.aurora.ui.theme.primary1

@Composable
fun ScheduleScreen(

){
    Text(
        text = "Schedule",
        fontSize = FontSize.HEADING1,
        color = primary1,
        modifier = Modifier.padding(30.dp)
    )
}