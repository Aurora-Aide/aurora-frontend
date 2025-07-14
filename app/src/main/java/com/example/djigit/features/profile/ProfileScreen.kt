package com.example.djigit.features.profile


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.djigit.R
import com.example.djigit.ui.theme.FontWeight
import com.example.djigit.ui.theme.functionalError

@Composable
fun ProfileScreen(showPopup: Boolean, onLogOutClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Header()

            Spacer(modifier = Modifier.height(8.dp))

            ProfileItem(stringResource(R.string.personal_information))
            ProfileItem(stringResource(R.string.car_details))
            ProfileItem(stringResource(R.string.my_reports))
            ProfileItem(stringResource(R.string.reports_about_me))

            Text(
                text = stringResource(R.string.log_out),
                color = functionalError,
                fontSize = 16.sp,
                fontWeight = FontWeight.PARAGRAPH1M,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onLogOutClicked() }
                    .padding(horizontal = 16.dp, vertical = 12.dp),
            )
        }

//        Box(modifier = Modifier.padding(bottom = 48.dp)) {
//            BottomNavigationBar()
//        }
    }

    if (showPopup) {
        LogoutPopup(
            onDismiss = { onLogOutClicked()},
            onConfirmLogout = {
                onLogOutClicked()
            }
        )
    }
}
