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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.aurora.ui.theme.primary1
import com.example.aurora.ui.theme.secondary2
import com.example.aurora.ui.theme.secondary5
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun ContainerBox(
    onContainerClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clickable { onContainerClick() }
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, baseBlue, RoundedCornerShape(12.dp))
            .background(baseBlue)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(id = R.drawable.mobile_check),
                contentDescription = "placeholder",
                modifier = Modifier
                    .height(172.dp)
            )
            Text(
                text = "This is your container",
                fontWeight = FontWeight.BODY2,
                fontSize = FontSize.BODY2,
                lineHeight = LineHeight.BODY2,
                color = secondary2

            )
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