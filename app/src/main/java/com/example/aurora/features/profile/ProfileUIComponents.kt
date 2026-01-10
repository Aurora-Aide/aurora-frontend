package com.example.aurora.features.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.aurora.R
import com.example.aurora.ui.theme.FontSize
import com.example.aurora.ui.theme.FontWeight
import com.example.aurora.ui.theme.LineHeight
import com.example.aurora.ui.theme.base0
import com.example.aurora.ui.theme.base100
import com.example.aurora.ui.theme.functionalError
import com.example.aurora.ui.theme.primary1
import com.example.aurora.ui.theme.secondary2
import com.example.aurora.ui.theme.secondary4

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

@Composable
fun Header(
    //isNotificationsVisible: Boolean = false,
    //isBackVisible: Boolean = false,
    title: String,
    //onBackClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.Center
    ) {
//        Box(Modifier.size(24.dp)) {
//            if (isBackVisible) {
//                Image(
//                    painter = painterResource(R.drawable.angl_left),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .rotate(180f)
//                        .size(24.dp)
//                        .clickable { onBackClick() }
//                )
//            }
//        }

        Text(
            text = title,
            color = primary1,
            fontSize = FontSize.BODY1,
            fontWeight = FontWeight.BODY1,
            lineHeight = LineHeight.BODY1,
        )

//        Box(Modifier.size(24.dp)) {
//            if (isNotificationsVisible) {
//                Image(
//                    painter = painterResource(R.drawable.notification_icon),
//                    contentDescription = null,
//                    modifier = Modifier.size(24.dp)
//                )
//            }
//        }
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
fun PersonalInfoItem(text1: String, text2: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .padding(start = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text1,
            color = primary1,
            fontSize = FontSize.PARAGRAPH1,
            fontWeight = FontWeight.PARAGRAPH1M
        )
        //Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text2,
            color = secondary4,
            fontSize = FontSize.PARAGRAPH1,
            fontWeight = FontWeight.PARAGRAPH1M
        )
        Spacer(modifier = Modifier.width(24.dp))
    }
    Image(
        painter = painterResource(R.drawable.divider_horizontal),
        contentDescription = null,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun ProfileFooter(
    onLogOutClick: () -> Unit,
    onDeleteAccountClick: () -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = {
                onLogOutClick()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = base0),
            border = BorderStroke(1.dp, primary1)

        ) {
            Text(
                text = "Log Out  ",
                fontSize = FontSize.PARAGRAPH2,
                fontWeight = FontWeight.PARAGRAPH2M,
                lineHeight = LineHeight.PARAGRAPH2,
                color = primary1
            )
            Image(
                painter = painterResource(id = R.drawable.log_out),
                contentDescription = "log_out",
                modifier = Modifier
                    .size(20.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                onDeleteAccountClick()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = base0),
            border = BorderStroke(1.dp, functionalError)

        ) {
            Text(
                text = "Delete Account  ",
                fontSize = FontSize.PARAGRAPH2,
                fontWeight = FontWeight.PARAGRAPH2M,
                lineHeight = LineHeight.PARAGRAPH2,
                color = functionalError,
            )
            Image(
                painter = painterResource(id = R.drawable.delete),
                contentDescription = "delete",
                modifier = Modifier
                    .size(20.dp)
            )
        }
    }
}

@Composable
fun BottomNavigationBar(
    onToHomeClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.Bottom
        ) {
            BottomNavItem(
                iconResId = R.drawable.home,
                label = stringResource(R.string.home),
                isSelected = false,
                onClick = { onToHomeClick() }
            )

            BottomNavItem(
                iconResId = R.drawable.profile,
                label = stringResource(R.string.profile),
                isSelected = true,
                onClick = {}
            )
        }
    }
}

@Composable
fun BottomNavItem(
    iconResId: Int,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) { // TODO the whole item to be clickable not only the image, add line between dif items
    val tintColor = if (isSelected) primary1 else secondary4

    Box(
        modifier = Modifier
            //.fillMaxHeight()
            .clickable { onClick() }
            //.border(1.dp, secondary5),

    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            //modifier = Modifier.clickable { onClick() }
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = label,
                modifier = Modifier.size(24.dp),
                alignment = Alignment.Center,
            )
            Text(
                text = label,
                fontSize = FontSize.PARAGRAPH3,
                fontWeight = FontWeight.PARAGRAPH3R,
                lineHeight = LineHeight.PARAGRAPH3,
                color = tintColor
            )
        }
    }

}

@Composable
fun LogoutPopup(
    onDismiss: () -> Unit,
    onConfirmLogout: () -> Unit,
    title: String,
    caption: String,
    buttonText: String,
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
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .padding(24.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = title,
                        color = primary1,
                        fontSize = FontSize.BODY2,
                        fontWeight = FontWeight.BODY2,
                        lineHeight = LineHeight.BODY2,
                    )

                    Text(
                        text = caption,
                        color = secondary2,
                        fontSize = FontSize.PARAGRAPH2,
                        fontWeight = FontWeight.PARAGRAPH2R,
                        lineHeight = LineHeight.PARAGRAPH2,
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
                                .padding(vertical = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(R.string.back),
                                color = primary1,
                                fontSize = FontSize.PARAGRAPH2,
                                fontWeight = FontWeight.PARAGRAPH2M,
                                lineHeight = LineHeight.PARAGRAPH2,
                            )
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .border(
                                    width = 1.dp,
                                    color = functionalError,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .clickable { onConfirmLogout() }
                                .padding(vertical = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = buttonText,
                                color = functionalError,
                                fontSize = FontSize.PARAGRAPH2,
                                fontWeight = FontWeight.PARAGRAPH2M,
                                lineHeight = LineHeight.PARAGRAPH2,
                            )
                        }
                    }
                }
            }
        }
    }
}

