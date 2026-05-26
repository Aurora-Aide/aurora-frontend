package com.example.aurora.features.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.aurora.R
import com.example.aurora.ui.theme.FontSize
import com.example.aurora.ui.theme.FontWeight
import com.example.aurora.ui.theme.LineHeight
import com.example.aurora.ui.theme.base0
import com.example.aurora.ui.theme.base100
import com.example.aurora.ui.theme.base90
import com.example.aurora.ui.theme.primary1
import com.example.aurora.ui.theme.secondary2
import com.example.aurora.ui.components.ErrorBanner
import com.example.aurora.ui.theme.functionalError

@Composable
fun LoginScreen(
    login: LoginData,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    isLoginSuccessful: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onSignUpClick: () -> Unit,
    //onGoogleClick: () -> Unit,
) {
    val scrollState = rememberScrollState()

    if (login.isLoginSuccessful) {
        LaunchedEffect(null) {
            isLoginSuccessful()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        LoginHeader()
        Spacer(modifier = Modifier.height(30.dp))
        LoginFields(
            login,
            onEmailChange = {
                onEmailChange(it)
            },
            onPasswordChange = {
                onPasswordChange(it)
            },
            onForgotPasswordClick = { onForgotPasswordClick() }
        )
        LoginFooter(
            login = login,
            onLogInClick = {
                onLoginClick()
            },
            onSignUpClick = {
                onSignUpClick()
            },
            //onGoogleClick = { onGoogleClick() },
            enabled = login.email.isNotEmpty() && login.password.isNotEmpty()
        )
    }
}

@Composable
fun LoginHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.aurora),
            contentDescription = "aurora",
            modifier = Modifier
                .width(170.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Log In",
            fontSize = FontSize.HEADING1,
            fontWeight = FontWeight.HEADING1,
            color = primary1
        )
    }
}

@Composable
fun LoginFields(
    login: LoginData,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    Column {
        TextField(
            value = login.email,
            label = "Email",
            error = login.isEmailError != LoginEmailErrors.NONE,
            placeholder = "Email",
            onValueChange = onEmailChange,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            errorText = login.isEmailError.value?.let { stringResource(it) } ?: ""
        )

        Spacer(modifier = Modifier.height(20.dp))

        var isVisible by remember { mutableStateOf(false) }

        TextField(
            value = login.password,
            label = "Password",
            error = login.isPasswordError != LoginPasswordErrors.NONE,
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
            errorText = login.isPasswordError.value?.let { stringResource(it) } ?: ""
        )

        ErrorBanner(login.errorMessage)

        Spacer(modifier = Modifier.height(20.dp))

//        TextButton(onClick = onForgotPasswordClick, modifier = Modifier.align(Alignment.End)) {
//            Text(
//                text = "Forgot Password?",
//                textDecoration = TextDecoration.Underline,
//                fontSize = FontSize.PARAGRAPH3,
//                fontWeight = FontWeight.PARAGRAPH3R,
//                lineHeight = LineHeight.PARAGRAPH3,
//                color = primary1
//            )
//        }

        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun LoginFooter(
    login: LoginData,
    onLogInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    //onGoogleClick: () -> Unit,
    enabled: Boolean,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = {
                onLogInClick()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(8.dp),
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(containerColor = primary1)

        ) {
            Text(
                text = if (login.isLoading) "Logging In..." else "Log In",
                fontSize = FontSize.PARAGRAPH1,
                fontWeight = FontWeight.PARAGRAPH1M,
                lineHeight = LineHeight.PARAGRAPH1,
                color = base0
            )
        }

        Row {
            TextButton(onClick = onSignUpClick) {
                Text(
                    text = "Don't have an account?  ",
                    fontSize = FontSize.PARAGRAPH3,
                    fontWeight = FontWeight.PARAGRAPH3R,
                    lineHeight = LineHeight.PARAGRAPH3,
                    color = base100
                )
                Text(
                    text = "Create Account",
                    textDecoration = TextDecoration.Underline,
                    fontSize = FontSize.PARAGRAPH3,
                    fontWeight = FontWeight.PARAGRAPH1M,
                    lineHeight = LineHeight.PARAGRAPH3,
                    color = primary1
                )
            }
        }

//        Spacer(modifier = Modifier.height(10.dp))
//
//        Row(
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically,
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.line),
//                contentDescription = "line",
//                modifier = Modifier
//                    .weight(1f)
//            )
//            Spacer(modifier = Modifier.width(4.dp))
//            Text(
//                text = " or ", fontSize = FontSize.PARAGRAPH2, fontWeight = FontWeight.PARAGRAPH2R,
//                lineHeight = LineHeight.PARAGRAPH2, color = base90
//            )
//            Spacer(modifier = Modifier.width(4.dp))
//            Image(
//                painter = painterResource(id = R.drawable.line),
//                contentDescription = "line",
//                modifier = Modifier
//                    .weight(1f)
//            )
//        }
//
//        Spacer(modifier = Modifier.height(30.dp))
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.google),
//                contentDescription = "Google",
//                modifier = Modifier
//                    .weight(1f)
//                    .clickable {
//                        onGoogleClick()
//                    }
//            )
//        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextField(
    value: String,
    label: String,
    error: Boolean,
    errorText: String,
    placeholder: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    trailingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        isError = error,
        onValueChange = onValueChange,
        label = {
            Text(text = label)
        },
        placeholder = {
            Text(text = placeholder, color = primary1)
        },
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        trailingIcon = trailingIcon,
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = secondary2,
            unfocusedBorderColor = secondary2,
            focusedLabelColor = secondary2,
        )
    )
    if (error) {
        Text(text = errorText, color = functionalError)
    }
}