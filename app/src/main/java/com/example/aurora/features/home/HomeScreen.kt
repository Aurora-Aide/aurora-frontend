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
import com.example.aurora.ui.theme.FontSize
import com.example.aurora.ui.theme.LineHeight
import com.example.aurora.ui.theme.FontWeight
import com.example.aurora.ui.theme.base10
import com.example.aurora.ui.theme.baseBlue
import com.example.aurora.ui.theme.primary1
import com.example.aurora.ui.theme.secondary2

@Composable
fun HomeScreen(
    onToProfileClick: () -> Unit,
) {

    val scrollState = rememberScrollState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.size(68.dp),
                onClick = { /* do something */ },
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
                text = "Hello, User", // TODO change "user" to their real first name
                color = primary1,
                fontSize = FontSize.BODY2,
                fontWeight = FontWeight.BODY2,
                lineHeight = LineHeight.BODY2,
                // modifier = Modifier.padding(8.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Your Containers:",
                color = primary1,
                fontSize = FontSize.HEADING3,
                fontWeight = FontWeight.HEADING3,
                lineHeight = LineHeight.HEADING3,
            )

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.Start
            ) {
                ContainerBox { }
                ContainerBox { }
                ContainerBox { }
                ContainerBox { }
                ContainerBox { }
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
