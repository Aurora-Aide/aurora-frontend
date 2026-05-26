package com.example.aurora.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.aurora.R
import com.example.aurora.features.profile.BottomNavItem
import com.example.aurora.ui.theme.FontSize
import com.example.aurora.ui.theme.FontWeight
import com.example.aurora.ui.theme.LineHeight
import com.example.aurora.ui.theme.baseBlue
import com.example.aurora.ui.theme.secondary2

@Composable
fun ContainerBox(
    dispenserName: String,
    dispenserId: String,
    onContainerClick: () -> Unit,
) {
    Surface (
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onContainerClick() },
        color = baseBlue,
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 0.dp,
        shadowElevation = 2.dp,
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Absolute.Left,
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(id = R.drawable.mobile_check),
                contentDescription = "placeholder",
                modifier = Modifier
                    .height(172.dp)
            )
            Spacer(modifier = Modifier.width(44.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = dispenserName,
                    fontWeight = FontWeight.BODY2,
                    fontSize = FontSize.BODY2,
                    lineHeight = LineHeight.BODY2,
                    color = secondary2
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.id_value, dispenserId),
                    fontWeight = FontWeight.PARAGRAPH2M,
                    fontSize = FontSize.PARAGRAPH2,
                    lineHeight = LineHeight.PARAGRAPH2,
                    color = secondary2
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}


@Composable
fun BottomNavigationBar(
    onToProfileClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()){
//        Image(
//            painter = painterResource(R.drawable.divider_horizontal),
//            contentDescription = null,
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
                //.padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.Bottom
        ) {
            BottomNavItem(
                iconResId = R.drawable.home,
                label = stringResource(R.string.home),
                isSelected = true,
                onClick = {}
            )

//            Image(
//                painter = painterResource(R.drawable.divider_vertical),
//                contentDescription = null,
//                modifier = Modifier.fillMaxWidth()
//            )

//        BottomNavItem(
//            iconResId = R.drawable.new_report,
//            label = stringResource(R.string.new_report),
//            isSelected = false,
//            onClick = {}
//        )

            BottomNavItem(
                iconResId = R.drawable.profile,
                label = stringResource(R.string.profile),
                isSelected = false,
                onClick = { onToProfileClick() }
            )
        }
    }
}