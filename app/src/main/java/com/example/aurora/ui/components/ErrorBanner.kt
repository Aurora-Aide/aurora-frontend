package com.example.aurora.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.aurora.ui.theme.FontSize
import com.example.aurora.ui.theme.FontWeight
import com.example.aurora.ui.theme.LineHeight
import com.example.aurora.ui.theme.functionalError

@Composable
fun ErrorBanner(message: String) {
    if (message.isBlank()) return
    Text(
        text = message,
        color = functionalError,
        fontSize = FontSize.PARAGRAPH2,
        fontWeight = FontWeight.PARAGRAPH2R,
        lineHeight = LineHeight.PARAGRAPH2,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}
