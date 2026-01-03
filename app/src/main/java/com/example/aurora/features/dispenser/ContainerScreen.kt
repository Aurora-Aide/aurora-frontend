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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.aurora.R
import com.example.aurora.ui.theme.FontSize
import com.example.aurora.ui.theme.FontWeight
import com.example.aurora.ui.theme.LineHeight
import com.example.aurora.ui.theme.baseBlue
import com.example.aurora.ui.theme.primary1

@Composable
fun ContainerScreen(
    container: ContainerData,
    showHideRename: Boolean,
    onScheduleClick: () -> Unit,
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
            Back(onBackClick)
            Spacer(modifier = Modifier.height(16.dp))
            ContainerHeaderCard(
                uiState = container,
                onEditClick = { onBackToContainerRenameClicked() }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Schedule( onScheduleClick )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    if (showHideRename) {
        DispenserPopup(
            onDismiss = { onBackToContainerRenameClicked() },
            onConfirmEdit = { onRenameConfirm() },
            title = "Rename pill:",
            caption = "Enter a new pill name for this slot.",
            onNameChange = { onRenameChange(it) },
            value = container.renameDraft,
            placeHolder = container.pillName,
            error = container.isRenameError,
        )
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
fun ContainerHeaderCard(
    uiState: ContainerData,
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
                        text = uiState.pillName,
                        fontSize = FontSize.HEADING3,
                        fontWeight = FontWeight.HEADING3,
                        lineHeight = LineHeight.HEADING3,
                        color = primary1
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "ID: ${uiState.slotNumber}",
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