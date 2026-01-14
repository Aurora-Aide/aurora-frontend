package com.example.aurora.features.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.aurora.R
import com.example.aurora.features.login.TextField
import com.example.aurora.features.signup.SignupNamesErrors
import com.example.aurora.ui.theme.FontSize
import com.example.aurora.ui.theme.FontWeight
import com.example.aurora.ui.theme.LineHeight
import com.example.aurora.ui.theme.base0
import com.example.aurora.ui.theme.primary1


@Composable
fun PersonalInfoScreen(
    personalInformationData: PersonalInformationData,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onUpdateNamesClick: () -> Unit,
    onBackClick: () -> Unit,
    isUpdateNamesSuccessful: () -> Unit,
) {

    if (personalInformationData.isUpdateNamesSuccessful) {
        LaunchedEffect(null) {
            isUpdateNamesSuccessful()
        }
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(16.dp, 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Back(onBackClick)

            Spacer(modifier = Modifier.weight(1f))

            Header(title = "Change your name/s:")

            Spacer(modifier = Modifier.height(40.dp))

            TextField(
                value = personalInformationData.firstName,
                onValueChange = onFirstNameChange,
                label = "First Name",
                placeholder = personalInformationData.firstName,
                errorText = personalInformationData.isFirstNameError.value?.let { stringResource(it) }?: "",
                error = personalInformationData.isFirstNameError != SignupNamesErrors.NONE,
                trailingIcon = {
                    IconButton(
                        onClick = {}
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.pen),
                            contentDescription = "pen",
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = personalInformationData.lastName,
                onValueChange = onLastNameChange,
                label = "Last name",
                placeholder = personalInformationData.lastName,
                errorText =  personalInformationData.isLastNameError.value?.let { stringResource(it) }?: "",
                error = personalInformationData.isLastNameError != SignupNamesErrors.NONE,
                trailingIcon = {
                    IconButton(
                        onClick = {}
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.pen),
                            contentDescription = "pen",
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    onUpdateNamesClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = base0),
                border = BorderStroke(1.dp, primary1),
                enabled = !personalInformationData.isLoading,

            ) {
                Text(
                    text = if (personalInformationData.isLoading) "Changing names...  " else "Change Names  ",
                    fontSize = FontSize.PARAGRAPH2,
                    fontWeight = FontWeight.PARAGRAPH2M,
                    lineHeight = LineHeight.PARAGRAPH2,
                    color = primary1
                )
                Image(
                    painter = painterResource(id = R.drawable.upload),
                    contentDescription = "update_names",
                    modifier = Modifier
                        .size(20.dp)
                )
            }
            Spacer(modifier = Modifier.weight(0.3f))
        }
    }


}