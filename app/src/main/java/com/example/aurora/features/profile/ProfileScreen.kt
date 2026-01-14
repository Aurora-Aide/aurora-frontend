package com.example.aurora.features.profile


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.aurora.R
import com.example.aurora.ui.theme.FontSize
import com.example.aurora.ui.theme.FontWeight
import com.example.aurora.ui.theme.LineHeight
import com.example.aurora.ui.theme.base10
import com.example.aurora.ui.theme.primary1

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
    //onSettings: () -> Unit,
    onToHomeClick: () -> Unit,
    personalInfo: PersonalInformationData
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
                Header(title = "Profile")

                Spacer(modifier = Modifier.height(16.dp))

                ProfileItem(stringResource(R.string.personal_information)) {
                    onPersonalInformation()
                }
                PersonalInfoItem("Names", personalInfo.firstName + " " + personalInfo.lastName)
                PersonalInfoItem("Email", personalInfo.email)

                Spacer(modifier = Modifier.height(20.dp))

//                ProfileItem("Settings") {
//                    onSettings()
//                } // TODO: Theme change, Notifications, Other???
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
