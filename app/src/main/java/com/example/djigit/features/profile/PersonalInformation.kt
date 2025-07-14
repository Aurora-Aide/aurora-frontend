package com.example.djigit.features.profile

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
import com.example.djigit.R
import com.example.djigit.features.login.TextField
import com.example.djigit.ui.theme.FontWeight
import com.example.djigit.ui.theme.base0
import com.example.djigit.ui.theme.functionalError
import com.example.djigit.ui.theme.primary1
import com.example.djigit.ui.theme.primary2
import com.example.djigit.ui.theme.secondary2


@Composable
fun PersonalInfoScreen(
    personalInformationData: PersonalInformationData,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onDeleteAccountClick: () -> Unit,
    onPasswordVisibilityChange: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Header(title = "Personal information", isBackVisible = true)

        Spacer(modifier = Modifier.height(20.dp))

        ProfileInitialsBox(initials = "JD")

        Spacer(modifier = Modifier.height(32.dp))

        TextField(
            value = personalInformationData.firstName,
            onValueChange = onFirstNameChange,
            label = "First Name",
            placeholder = "First Name",
            errorText = "Error",
            error = false,
            trailingIcon = {
                IconButton(
                    onClick = {
                        onPasswordVisibilityChange()
                    }
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
            label = "Last Name",
            placeholder = "Last Name",
            errorText = "Error",
            error = false,
            trailingIcon = {
                IconButton(
                    onClick = {
                       onPasswordVisibilityChange()
                    }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.pen),
                        contentDescription = "pen",
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(30.dp))

        TextField(
            value = personalInformationData.email,
            label = "Email",
            error = false,
            placeholder = "Email",
            onValueChange = onEmailChange,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            errorText = "Error",
            trailingIcon = {
                IconButton(
                    onClick = {
                        onPasswordVisibilityChange()
                    }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.pen),
                        contentDescription = "pen",
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        var isVisible by remember { mutableStateOf(false) }

        TextField(
            value = personalInformationData.password,
            label = "Password",
            error = false,
            placeholder = "Password",
            onValueChange = onPasswordChange,
            visualTransformation = if (isVisible) VisualTransformation.None
            else PasswordVisualTransformation('\u002A'),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Go
            ),
            trailingIcon = {
                IconButton(
                    onClick = {
                        isVisible = !isVisible
                    }
                ) {
                    Image(
                        imageVector = if (isVisible) Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff,
                        contentDescription = if (isVisible) "Hide password"
                        else "Show password"
                    )
                }
            },
            errorText = "Error"
        )
        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = functionalError,
                    shape = RoundedCornerShape(4.dp)
                )
                .clickable {
                    onDeleteAccountClick()
                }
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.delete_account),
                color = functionalError,
                fontSize = 14.sp,
                fontWeight = FontWeight.PARAGRAPH1M
            )
            Spacer(modifier = Modifier.width(10.dp))
            Image(
                painter = painterResource(id = R.drawable.trash),
                contentDescription = null
            )
        }
    }
}

@Composable
fun ProfileInitialsBox(
    initials: String,
    modifier: Modifier = Modifier,
    onEditClick: () -> Unit = {}
) {
    Box(modifier = modifier.size(80.dp)) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(primary2),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = initials,
                fontSize = 32.sp,
                fontWeight = Bold,
                color = secondary2
            )
        }

        Box(
            modifier = Modifier
                .size(28.dp)
                .align(Alignment.BottomEnd)
                .clip(RoundedCornerShape(8.dp))
                .background(base0),
            contentAlignment = Alignment.Center
        ) {
            Box(
                Modifier
                    .size(26.dp)
                    .align(Alignment.BottomEnd)
                    .clip(RoundedCornerShape(8.dp))
                    .background(primary1)
            ) {
                IconButton(
                    onClick = onEditClick,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = painterResource(R.drawable.pen).toString(),
                        tint = base0,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
