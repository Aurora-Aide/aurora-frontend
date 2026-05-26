package com.example.aurora.features.admin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.aurora.R
import com.example.aurora.ui.theme.FontSize
import com.example.aurora.ui.theme.FontWeight
import com.example.aurora.ui.theme.LineHeight
import com.example.aurora.ui.theme.baseBlue
import com.example.aurora.ui.theme.secondary2
import com.example.aurora.ui.theme.secondary4

@Composable
fun DispenserBox(
    name: String,
    id: String,
    serialId: String?,
    owner: String?,
    size: String?,
    model: String?,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        color = baseBlue,
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 0.dp,
        shadowElevation = 2.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.mobile_check),
                contentDescription = "dispenser",
                modifier = Modifier
                    .width(72.dp)
                    .height(72.dp)
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = name,
                    fontWeight = FontWeight.BODY2,
                    fontSize = FontSize.BODY2,
                    lineHeight = LineHeight.BODY2,
                    color = secondary2
                )
                Text(
                    text = stringResource(R.string.id_value, id),
                    fontWeight = FontWeight.PARAGRAPH2M,
                    fontSize = FontSize.PARAGRAPH2,
                    lineHeight = LineHeight.PARAGRAPH2,
                    color = secondary2
                )
                Text(
                    text = stringResource(
                        R.string.serial_value,
                        serialId ?: stringResource(R.string.not_available_short)
                    ),
                    fontWeight = FontWeight.PARAGRAPH2M,
                    fontSize = FontSize.PARAGRAPH2,
                    lineHeight = LineHeight.PARAGRAPH2,
                    color = secondary2
                )
                Text(
                    text = stringResource(
                        R.string.owner_value,
                        owner ?: stringResource(R.string.not_available_short)
                    ),
                    fontWeight = FontWeight.PARAGRAPH2R,
                    fontSize = FontSize.PARAGRAPH2,
                    lineHeight = LineHeight.PARAGRAPH2,
                    color = secondary4
                )
                Text(
                    text = stringResource(
                        R.string.size_model_summary,
                        size ?: stringResource(R.string.not_available_short),
                        model ?: stringResource(R.string.not_available_short)
                    ),
                    fontWeight = FontWeight.PARAGRAPH2R,
                    fontSize = FontSize.PARAGRAPH2,
                    lineHeight = LineHeight.PARAGRAPH2,
                    color = secondary4
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

