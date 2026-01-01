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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aurora.R
import com.example.aurora.features.login.TextField
import com.example.aurora.ui.theme.FontSize
import com.example.aurora.ui.theme.FontWeight
import com.example.aurora.ui.theme.LineHeight
import com.example.aurora.ui.theme.base0
import com.example.aurora.ui.theme.functionalError
import com.example.aurora.ui.theme.primary1
import com.example.aurora.ui.theme.primary2
import com.example.aurora.ui.theme.secondary2


@Composable
fun PersonalInfoScreen(
    personalInformationData: PersonalInformationData,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onUpdateNamesClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
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
            errorText = "Error",
            error = false,
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
            errorText = "Error",
            error = false,
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
            border = BorderStroke(1.dp, primary1)

        ) {
            Text(
                text = "Change Names  ",
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