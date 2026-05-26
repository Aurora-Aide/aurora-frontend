package com.example.aurora.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.aurora.ui.UiMessage
import com.example.aurora.ui.theme.FontSize
import com.example.aurora.ui.theme.FontWeight
import com.example.aurora.ui.theme.LineHeight
import com.example.aurora.ui.theme.functionalError

@Composable
fun ErrorBanner(@StringRes messageRes: Int) {
    if (messageRes == UiMessage.NONE) return
    Text(
        text = stringResource(messageRes),
        color = functionalError,
        fontSize = FontSize.PARAGRAPH2,
        fontWeight = FontWeight.PARAGRAPH2R,
        lineHeight = LineHeight.PARAGRAPH2,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}
