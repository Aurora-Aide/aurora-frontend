package com.example.aurora.features.home

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
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.aurora.features.login.LoginData
import com.example.aurora.ui.theme.FontSize
import com.example.aurora.ui.theme.LineHeight
import com.example.aurora.ui.theme.FontWeight
import com.example.aurora.ui.theme.base10
import com.example.aurora.ui.theme.baseBlue
import com.example.aurora.ui.theme.primary1
import com.example.aurora.ui.theme.secondary2
import com.example.aurora.ui.theme.secondary4

@Composable
fun HomeScreen(
    onToProfileClick: () -> Unit,
    onAddDispenserClick: () -> Unit,
    name: String,
    onToDispenserClick: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.size(68.dp),
                onClick = { onAddDispenserClick() },
                containerColor = baseBlue,
                contentColor = secondary2,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
                shape = CircleShape
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
                text = "Hello, $name",
                color = primary1,
                fontSize = FontSize.BODY2,
                fontWeight = FontWeight.BODY2,
                lineHeight = LineHeight.BODY2,
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Your Dispensers:",
                color = primary1,
                fontSize = FontSize.HEADING3,
                fontWeight = FontWeight.HEADING3,
                lineHeight = LineHeight.HEADING3,
            )

            Spacer(modifier = Modifier.height(20.dp))

            if(dispensers.dispensers.isEmpty()){
                Text(
                    text = "You don't have any dispensers! You can add your dispenser by clicking the + button on the bottom right of this page",
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
                ContainerBox { onToDispenserClick() }
                ContainerBox { onToDispenserClick() }
                ContainerBox { onToDispenserClick() }
                ContainerBox { onToDispenserClick() }
                ContainerBox { onToDispenserClick() }
            }
        }
    }

//    Box(modifier = Modifier
//            //.padding(bottom = 4.dp)
//            .fillMaxWidth(),
//            contentAlignment = Alignment.BottomEnd)
//        {
//            BottomNavigationBar(onToProfileClick = onToProfileClick)
//        }
}
