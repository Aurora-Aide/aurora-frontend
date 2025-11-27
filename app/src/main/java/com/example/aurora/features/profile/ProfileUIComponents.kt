package com.example.aurora.features.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.aurora.R
import com.example.aurora.ui.theme.FontWeight
import com.example.aurora.ui.theme.base100
import com.example.aurora.ui.theme.primary1
import com.example.aurora.ui.theme.secondary2


@Composable
fun Header(
    isNotificationsVisible: Boolean = false,
    isBackVisible: Boolean = false,
    title: String,
    onBackClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(Modifier.size(24.dp)) {
            if (isBackVisible) {
                Image(
                    painter = painterResource(R.drawable.angl_left),
                    contentDescription = null,
                    modifier = Modifier
                        .rotate(180f)
                        .size(24.dp)
                        .clickable { onBackClick() }
                )
            }
        }

        Text(
            text = title,
            fontSize = 20.sp,
            color = primary1,
            fontWeight = FontWeight.PARAGRAPH1M,
        )

        Box(Modifier.size(24.dp)) {
            if (isNotificationsVisible) {
                Image(
                    painter = painterResource(R.drawable.notification_icon),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun ProfileItem(text: String, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            color = primary1,
            fontSize = 16.sp,
            fontWeight = FontWeight.PARAGRAPH1M
        )
        Image(
            painter = painterResource(R.drawable.angl_left),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun BottomNavigationBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavItem(
            iconResId = R.drawable.home,
            label = stringResource(R.string.home),
            isSelected = false,
            onClick = {}
        )

        BottomNavItem(
            iconResId = R.drawable.new_report,
            label = stringResource(R.string.new_report),
            isSelected = false,
            onClick = {}
        )

        BottomNavItem(
            iconResId = R.drawable.profile,
            label = stringResource(R.string.profile),
            isSelected = true,
            onClick = {}
        )
    }
}

@Composable
fun BottomNavItem(
    iconResId: Int,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val tintColor = if (isSelected) primary1 else Color(0xFF1A0759)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = label,
            modifier = Modifier.size(28.dp),
            alignment = Alignment.Center,
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = tintColor
        )
    }
}

@Composable
fun LogoutPopup(
    onDismiss: () -> Unit,
    onConfirmLogout: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(base100.copy(0.3f))
            .clickable { onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        Popup(
            alignment = Alignment.Center,
            onDismissRequest = onDismiss,
            properties = PopupProperties(focusable = true)
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .background(Color.White, RoundedCornerShape(4.dp))
                    .padding(24.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.log_out),
                        color = primary1,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.PARAGRAPH1M,
                    )

                    Text(
                        text = stringResource(R.string.are_you_sure_you_want_to_log_out),
                        color = secondary2,
                        fontSize = 14.sp,
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .border(
                                    width = 1.dp,
                                    color = primary1,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .clickable { onDismiss() }
                                .padding(vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(R.string.back_to_profile),
                                color = primary1,
                                fontSize = 14.sp
                            )
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(primary1, RoundedCornerShape(4.dp))
                                .clickable { onConfirmLogout() }
                                .padding(vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Log Out",
                                color = Color.White,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

