package com.example.aurora.features.profile


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aurora.R
import com.example.aurora.ui.theme.FontSize
import com.example.aurora.ui.theme.FontWeight
import com.example.aurora.ui.theme.LineHeight
import com.example.aurora.ui.theme.functionalError
import com.example.aurora.ui.theme.primary1

@Composable
fun ProfileScreen(
    showPopupLogOut: Boolean,
    showPopupDelete: Boolean,
    onBackToProfileLogClicked: () -> Unit,
    onBackToProfileDeleteClicked: () -> Unit,
    onLogOutClicked: () -> Unit,
    onDeleteAccountClicked: () -> Unit,
    onPersonalInformation: () -> Unit,
    onSettings: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            //.navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)
        ) {
            Header(isNotificationsVisible = false, title = "Profile")

            Spacer(modifier = Modifier.height(16.dp))

            ProfileItem(stringResource(R.string.personal_information)) {
                onPersonalInformation()
            }
            PersonalInfoItem("Names", "First Last")
            PersonalInfoItem("Email", "your_email@mail.com")

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.your_dispensers),
                    color = primary1,
                    fontSize = FontSize.BODY2,
                    fontWeight = FontWeight.BODY2,
                    lineHeight = LineHeight.BODY2,
                )
            }

            DispenserItem("Name1", "Id 1 of dis")
            DispenserItem("Name2", "Id 2 of dis")
            DispenserItem("Name3", "Id 3 of dis")

            Spacer(modifier = Modifier.height(20.dp))

            ProfileItem("Settings") {
                onSettings()
            } // TODO: Theme change, Notifications, Other???
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(modifier = Modifier
            .padding(vertical = 16.dp, horizontal = 16.dp),
            verticalArrangement =  Arrangement.Bottom
        )
        {
            ProfileFooter(onLogOutClicked, onDeleteAccountClicked)
        }

//        Box(modifier = Modifier.padding(bottom = 48.dp)) {
//            BottomNavigationBar()
//        }
    }

    if (showPopupLogOut) {
        LogoutPopup(
            onDismiss = { onBackToProfileLogClicked() },
            onConfirmLogout = {
                onLogOutClicked()
            }
        )
    }
    if (showPopupDelete) {
        LogoutPopup(
            onDismiss = { onBackToProfileDeleteClicked() },
            onConfirmLogout = {
                onDeleteAccountClicked()
            }
        )
    }
}
