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
import com.example.aurora.ui.theme.base10
import com.example.aurora.ui.theme.baseBlue
import com.example.aurora.ui.theme.functionalError
import com.example.aurora.ui.theme.primary1
import com.example.aurora.ui.theme.secondary2

@Composable
fun ProfileScreen(
    showPopupLogOut: Boolean,
    showPopupDelete: Boolean,
    onBackToProfileLogClicked: () -> Unit,
    onBackToProfileDeleteClicked: () -> Unit,
    onLogOutClicked: () -> Unit,
    onDeleteAccountClicked: () -> Unit,
    onLogOut: () -> Unit,
    onDeleteAccount: () -> Unit,
    onPersonalInformation: () -> Unit,
    onSettings: () -> Unit,
    onToHomeClick: ()-> Unit,
) {

    val scrollState = rememberScrollState()

    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = base10,
                modifier = Modifier.height(64.dp)
            ) {
                BottomNavigationBar(onToHomeClick = onToHomeClick)
            }
        }
    )
    { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 16.dp)
                    .verticalScroll(scrollState)
            ) {
                Header(isNotificationsVisible = false, title = "Profile")

                Spacer(modifier = Modifier.height(16.dp))

                ProfileItem(stringResource(R.string.personal_information)) {
                    onPersonalInformation()
                }
                PersonalInfoItem("Names", "First Last")  // TODO change text2 to actual data
                PersonalInfoItem(
                    "Email",
                    "your_email@mail.com"
                )  // TODO change text2 to actual data

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

                // TODO change both texts to real data and place however many dispensers there are
                // use showAllUserDispensers from backend

                DispenserItem("Name1", "Id 1 of dis")
                DispenserItem("Name2", "Id 2 of dis")
                DispenserItem("Name3", "Id 3 of dis")

                Spacer(modifier = Modifier.height(20.dp))

                ProfileItem("Settings") {
                    onSettings()
                } // TODO: Theme change, Notifications, Other???
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.Bottom
            )
            {
                ProfileFooter(onLogOut, onDeleteAccount)
            }
        }
    }

    if (showPopupLogOut) {
        LogoutPopup(
            onDismiss = { onBackToProfileLogClicked() },
            onConfirmLogout = {
                onLogOutClicked()
            },
            title = stringResource(R.string.log_out),
            caption = stringResource(R.string.are_you_sure_you_want_to_log_out),
            buttonText = stringResource(R.string.log_out)
        )
    }
    if (showPopupDelete) {
        LogoutPopup(
            onDismiss = { onBackToProfileDeleteClicked() },
            onConfirmLogout = {
                onDeleteAccountClicked()
            },
            title = stringResource(R.string.delete_account),
            caption = stringResource(R.string.are_you_sure_you_want_to_delete_your_account),
            buttonText = stringResource(R.string.delete_account)
        )
    }
}
