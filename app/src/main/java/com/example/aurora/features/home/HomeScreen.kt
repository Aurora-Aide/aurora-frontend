package com.example.aurora.features.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.aurora.R
import com.example.aurora.ui.theme.FontSize
import com.example.aurora.ui.theme.LineHeight
import com.example.aurora.ui.theme.FontWeight
import com.example.aurora.ui.theme.base10
import com.example.aurora.ui.theme.baseLightBlue
import com.example.aurora.ui.theme.baseTransparentBlue
import com.example.aurora.ui.theme.functionalError
import com.example.aurora.ui.theme.primary1
import com.example.aurora.ui.theme.secondary2
import com.example.aurora.ui.theme.secondary4
import com.example.aurora.ui.components.ErrorBanner


@Composable
fun HomeScreen(
    onToProfileClick: () -> Unit,
    onAddDispenserClick: () -> Unit,
    name: String,
    onToDispenserClick: (dispenserId: String, dispenserName: String) -> Unit,
    dispensers: DispensersData,
) {
    val scrollState = rememberScrollState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.size(68.dp),
                onClick = { onAddDispenserClick() },
                containerColor = baseLightBlue,
                contentColor = secondary2,
                elevation = FloatingActionButtonDefaults.elevation(4.dp),
                shape = CircleShape,
            ) {
                Icon(Icons.Filled.Add, "Add Dispenser")
            }
        },
        bottomBar = {
            BottomAppBar (
                containerColor = base10,
                modifier = Modifier.height(64.dp)
            ){
                BottomNavigationBar(onToProfileClick = onToProfileClick)
            }
        },
    )
    { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
        )
        {
            Text(
                text = stringResource(R.string.hello_name, name),
                color = primary1,
                fontSize = FontSize.BODY2,
                fontWeight = FontWeight.BODY2,
                lineHeight = LineHeight.BODY2,
            )

            Spacer(modifier = Modifier.height(20.dp))

            ErrorBanner(dispensers.errorMessage)

            Text(
                text = stringResource(R.string.your_dispensers_colon),
                color = primary1,
                fontSize = FontSize.HEADING3,
                fontWeight = FontWeight.HEADING3,
                lineHeight = LineHeight.HEADING3,
            )

            Spacer(modifier = Modifier.height(20.dp))

            if(dispensers.dispensers.isEmpty()){
                Text(
                    text = stringResource(R.string.no_dispensers_hint),
                    color = secondary4,
                    fontSize = FontSize.PARAGRAPH2,
                    fontWeight = FontWeight.PARAGRAPH2R,
                    lineHeight = LineHeight.PARAGRAPH2,
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.Start
            ) {
                dispensers.dispensers.forEach { dispenser ->
                    ContainerBox(
                        dispenserName = dispenser.name,
                        dispenserId = dispenser.id.toString(),
                        onContainerClick = { onToDispenserClick(dispenser.id.toString(), dispenser.name) }
                    )
                }
            }
        }
    }
}