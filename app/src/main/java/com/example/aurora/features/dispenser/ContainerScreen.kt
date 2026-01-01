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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.aurora.R
import com.example.aurora.ui.theme.FontSize
import com.example.aurora.ui.theme.FontWeight
import com.example.aurora.ui.theme.LineHeight
import com.example.aurora.ui.theme.primary1

@Composable
fun ContainerScreen(
    name: String,
    onScheduleClick: () -> Unit,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
)
{
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        Arrangement.Top
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 16.dp)
                .verticalScroll(scrollState)
        ) {
            ContainerHeader(
                onBackClick = onBackClick,
                name = name,
                onEditClick = onEditClick
            )
            Spacer(modifier = Modifier.height(16.dp))
            Schedule( onScheduleClick )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun Schedule(onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Schedule",
            color = primary1,
            fontSize = FontSize.BODY2,
            fontWeight = FontWeight.BODY2,
            lineHeight = LineHeight.BODY2
        )
        Image(
            painter = painterResource(R.drawable.angl_left),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun ContainerHeader(
    onBackClick: () -> Unit,
    name: String,
    onEditClick: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.backarrow),
                contentDescription = "back",
                modifier = Modifier
                    .size(28.dp)
                    .clickable { onBackClick() }
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = name,
                fontSize = FontSize.HEADING3,
                fontWeight = FontWeight.HEADING3,
                lineHeight = LineHeight.HEADING3,
                color = primary1
            )
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(R.drawable.pen),
                contentDescription = "pen",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onEditClick() }
            )
        }
    }
}

