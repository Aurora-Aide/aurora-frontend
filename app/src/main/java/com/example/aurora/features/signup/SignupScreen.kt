package com.example.aurora.features.signup

import com.example.aurora.features.login.LoginEmailErrors
import com.example.aurora.features.login.LoginPasswordErrors
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aurora.R
import com.example.aurora.features.login.TextField
import com.example.aurora.navigation.Routes.MainRoute.Google.toGoogle
import com.example.aurora.navigation.Routes.MainRoute.Login.toLogIn
import com.example.aurora.ui.components.ErrorBanner
import com.example.aurora.ui.theme.*
import org.koin.core.module._singleInstanceFactory
import kotlin.math.sign

@Composable
fun SignupScreen(
    navController: NavController,
    signup: SignupData,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSecondPasswordChange: (String) -> Unit,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    //onModelNumChange: (String) -> Unit,
    onContinueClick: () -> Unit,
    onCreateAccountClick: () -> Unit,
    isSignupSuccessful: () -> Unit,
    onBackClick:() ->Unit,
    ){
    val scrollState = rememberScrollState()

    if(signup.isSignupSuccessful){
        LaunchedEffect(null) {
            isSignupSuccessful()
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
        if(signup.isFirstStep){
            SignupHeader(
                signup,
                isBackVisible = false,
                onBackClick
            )

            Spacer(modifier = Modifier.height(30.dp))

            SignupFields(
                signup,
                onEmailChange = {
                    onEmailChange(it)
                },
                onPasswordChange = {
                    onPasswordChange(it)
                },
                onSecondPasswordChange = {
                    onSecondPasswordChange(it)
                },
            )

            SignupFooter(
                onSignupClick = {
                    onContinueClick()
                },
                onLoginClick = {
                    navController.toLogIn()
                },
                onGoogleClick = {
                    navController.toGoogle()
                },
                enabled = (signup.email.isNotEmpty() && signup.password.isNotEmpty() && signup.passwordRepeat.isNotEmpty())
            )
        } else{
            SignupHeader(
                signup,
                isBackVisible = true,
                onBackClick
            )

            Spacer(modifier = Modifier.height(30.dp))

            AddNamesFields(
                signup,
                onFirstNameChange = {
                    onFirstNameChange(it)
                },
                onLastNameChange = {
                    onLastNameChange(it)
                }
            )
            LogNamesFooter(
                signup,
                onCreateAccountClick = {
                    onCreateAccountClick()
                },
                enabled = (signup.firstName.isNotEmpty() &&
                        signup.lastName.isNotEmpty()) &&
                        !signup.isLoading
            )
        }
    }
}

@Composable
fun SignupHeader(
    signup: SignupData,
    isBackVisible: Boolean,
    onBackClick:() -> Unit
) {
    if(isBackVisible){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start
        ){
            Image(painter = painterResource(id = R.drawable.backarrow),
                contentDescription = "back",
                modifier = Modifier
                    .width(24.dp)
                    .clickable {
                        onBackClick()
                    }
            )
        }

    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = R.drawable.aurora),
            contentDescription = "aurora",
            modifier = Modifier
                .width(150.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Create Account",
            fontSize = FontSize.HEADING1,
            fontWeight = FontWeight.HEADING1,
            color = primary1
        )
    }
}

@Composable
fun SignupFields(
    signup: SignupData,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSecondPasswordChange: (String) -> Unit,
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = "Personal Information", fontSize = FontSize.BODY2, fontWeight = FontWeight.BODY2,
                lineHeight = LineHeight.BODY2, color = secondary2)
            Row{
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(shape = CircleShape)
                        .background(color = secondary2),
                    contentAlignment = Alignment.Center
                ){
                    Text(text = "1",
                        fontSize = FontSize.PARAGRAPH1,
                        fontWeight = FontWeight.PARAGRAPH1M,
                        lineHeight = LineHeight.PARAGRAPH1,
                        color = base0)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .border(
                            width = 1.dp,
                            color = secondary2,
                            shape = CircleShape
                        )
                        .clip(shape = CircleShape)
                        .background(color = base0)
                        .clickable { signup.isFirstStep = false },
                    contentAlignment = Alignment.Center,

                ){
                    Text(text = "2",
                        fontSize = FontSize.PARAGRAPH1,
                        fontWeight = FontWeight.PARAGRAPH1M,
                        lineHeight = LineHeight.PARAGRAPH1,
                        color = secondary2)
                }
            }

        }
        TextField(
            value = signup.email,
            label = "Email*",
            error = signup.isEmailError != LoginEmailErrors.NONE,
            placeholder = "Email",
            onValueChange = onEmailChange,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            errorText = signup.isEmailError.value?.let { stringResource(it) } ?: ""
        )

        Spacer(modifier = Modifier.height(20.dp))

        val isVisible1 = remember { mutableStateOf(false) }

        //signup.isPasswordError.value?.let { stringResource(it) } ?: ""
        TextField(
            value = signup.password,
            label = "Password*",
            error = signup.isPasswordError != LoginPasswordErrors.NONE,
            placeholder = "Password",
            onValueChange = onPasswordChange,
            visualTransformation = if (isVisible1.value) VisualTransformation.None
            else PasswordVisualTransformation('\u002A'),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Go
            ),
            trailingIcon = {
                IconButton(
                    onClick = {
                        isVisible1.value = !isVisible1.value
                    }
                ) {
                    Image(
                        imageVector = if (isVisible1.value) Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff,
                        contentDescription = if (isVisible1.value) "Hide password"
                        else "Show password"
                    )
                }
            },
            errorText = signup.isPasswordError.value?.let { stringResource(it) } ?: ""
        )


        Spacer(modifier = Modifier.height(20.dp))

        val isVisible2 = remember { mutableStateOf(false) }

        TextField(
            value = signup.passwordRepeat,
            label = "Repeat Password*",
            error = signup.isPasswordRepeatError != LoginPasswordErrors.NONE,
           // error = isPasswordError2.value,
            placeholder = "Repeat Password",
            onValueChange = onSecondPasswordChange,
            visualTransformation = if (isVisible2.value) VisualTransformation.None
            else PasswordVisualTransformation('\u002A'),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Go
            ),
            trailingIcon = {
                IconButton(
                    onClick = {
                        isVisible2.value = !isVisible2.value
                    }
                ) {
                    Image(
                        imageVector = if (isVisible2.value) Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff,
                        contentDescription = if (isVisible2.value) "Hide password"
                        else "Show password"
                    )
                }
            },
            errorText = signup.isPasswordRepeatError.value?.let{ stringResource(it) } ?: ""
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun AddNamesFields(
    signup: SignupData,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Your names?",
                fontSize = FontSize.BODY2,
                fontWeight = FontWeight.BODY2,
                lineHeight = LineHeight.BODY2,
                color = secondary2
            )
            Row {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(shape = CircleShape)
                        .background(color = secondary2)
                        .clickable { signup.isFirstStep = true },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "1",
                        fontSize = FontSize.PARAGRAPH1,
                        fontWeight = FontWeight.PARAGRAPH1M,
                        lineHeight = LineHeight.PARAGRAPH1,
                        color = base0)
                }
                Spacer(modifier = Modifier.width(4.dp))
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(shape = CircleShape)
                        .background(color = secondary2),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "2",
                        fontSize = FontSize.PARAGRAPH1,
                        fontWeight = FontWeight.PARAGRAPH1M,
                        lineHeight = LineHeight.PARAGRAPH1,
                        color = base0)
                }
            }

        }
        TextField(
            value = signup.firstName,
            label = "First Name",
            error = signup.isFirstNameError != SignupNamesErrors.NONE,
            placeholder = "First Name",
            onValueChange = onFirstNameChange,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            errorText = signup.isFirstNameError.value?.let{ stringResource(it) } ?: ""
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = signup.lastName,
            label = "Last Name",
            error = signup.isLastNameError != SignupNamesErrors.NONE,
            placeholder = "Last Name",
            onValueChange = onLastNameChange,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            errorText = signup.isLastNameError.value?.let{ stringResource(it) } ?: ""
        )

        ErrorBanner(signup.error)

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun SignupFooter(
    onSignupClick: () -> Unit,
    onLoginClick: () -> Unit,
    onGoogleClick: () -> Unit,
    enabled: Boolean,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = {
                onSignupClick()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(8.dp),
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(containerColor = primary1)

        ) {
            Text(
                text = "Continue",
                fontSize = FontSize.PARAGRAPH1,
                fontWeight = FontWeight.PARAGRAPH1M,
                lineHeight = LineHeight.PARAGRAPH1,
                color = base0
            )
        }

        Row {
            TextButton(onClick = onLoginClick) {
                Text(
                    text = "Already have an account?  ",
                    fontSize = FontSize.PARAGRAPH2,
                    fontWeight = FontWeight.PARAGRAPH2R,
                    lineHeight = LineHeight.PARAGRAPH2,
                    color = base100
                )
                Text(
                    text = "Log In",
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
//                text = " or ",
//                fontSize = FontSize.PARAGRAPH2,
//                fontWeight = FontWeight.PARAGRAPH2R,
//                lineHeight = LineHeight.PARAGRAPH2,
//                color = base90
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
//            Image(painter = painterResource(id = R.drawable.google),
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

@Composable
fun LogNamesFooter(
    signup: SignupData,
    onCreateAccountClick: () -> Unit,
    enabled: Boolean
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = {
                onCreateAccountClick()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(8.dp),
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(containerColor = primary1)

        ) {
            Text(
                text = if (signup.isLoading) "Creating Account..." else "Create Account",
                fontSize = FontSize.PARAGRAPH1,
                fontWeight = FontWeight.PARAGRAPH1M,
                lineHeight = LineHeight.PARAGRAPH1,
                color = base0
            )
        }
    }
}