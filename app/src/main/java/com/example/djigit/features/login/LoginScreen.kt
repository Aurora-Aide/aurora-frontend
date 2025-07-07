package com.example.djigit.features.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.djigit.R
import com.example.djigit.navigation.Routes.MainRoute.ForgotPassword.toForgotPassword
import com.example.djigit.navigation.Routes.MainRoute.Home.toHome
import com.example.djigit.navigation.Routes.MainRoute.SignUp.toSignUp
//import com.example.djigit.data.postLoginData
import com.example.djigit.navigation.Routes.MainRoute.Facebook.toFacebook
import com.example.djigit.navigation.Routes.MainRoute.Google.toGoogle

@Composable
fun LoginScreen(navController: NavController, login: LoginData, onEmailChange: (String) -> Unit,
                onPasswordChange: (String) -> Unit, onLoginClick: () -> Unit, isLoginSuccessful: () -> Unit) {
    val scrollState = rememberScrollState()

    if(login.isLoginSuccessful){
        navController.toHome()
        isLoginSuccessful()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.backgroundimage),
            contentDescription = "Login",
            modifier = Modifier
                .fillMaxSize()
                .blur(8.dp),
            contentScale = ContentScale.Crop
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier
                .padding(28.dp)
                .alpha(0.7f)
                .clip(
                    CutCornerShape(
                        topStart = 10.dp,
                        topEnd = 10.dp,
                        bottomStart = 10.dp,
                        bottomEnd = 10.dp
                    )
                )
                .background(MaterialTheme.colorScheme.background)
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier
                    .padding(48.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val context = LocalContext.current
                val response = remember { mutableStateOf("") }
                val emailError = remember { mutableStateOf(false) }
                val passError = remember { mutableStateOf(false) }


                LoginHeader()
                Spacer(modifier = Modifier.height(20.dp))
                LoginFields(
                    login,
                    emailError = emailError.value,
                    passError = passError.value,
                    onEmailChange = {
                        onEmailChange(it)
                        //email.value = it
                        if (it.isNotEmpty()) {
                            emailError.value = false
                        }
                    },
                    onPasswordChange = {
                        onPasswordChange(it)
                        if (it.isNotEmpty()) {
                            passError.value = false
                        }
                    },
                    onForgotPasswordClick = { navController.toForgotPassword() }
                )
                LoginFooter(
                    onSignInClick = {
                        onLoginClick()
                    },
                    onSignUpClick = {
                        navController.toSignUp()
                    },
                    onGoogleClick = {
                        navController.toGoogle()
                    },
                    onFacebookClick = {
                        navController.toFacebook()
                    },
                    login = login,
                    setError = {
                        emailError.value = true
                        passError.value = true
                    }
                )
            }
        }

    }


}

@Composable
fun LoginHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Welcome Back", fontSize = 36.sp, fontWeight = FontWeight.ExtraBold)
        Text(text = "Log in to continue", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun LoginFields(
    login: LoginData,
    emailError: Boolean,
    passError: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    val email = login.email
    val password = login.password
    Column {
        TextField(
            value = email,
            label = "Email",
            error = emailError && email.isEmpty(),
            placeholder = "Enter your email",
            //isVisible = remember { mutableStateOf(false) },
            onValueChange = onEmailChange,
            leadingIcon = {
                Icon(Icons.Default.Email, contentDescription = "Email")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            setError = { email.isNotEmpty() }
        )

        Spacer(modifier = Modifier.height(10.dp))

        var isVisible by remember { mutableStateOf(false) }

        TextField(
            value = password,
            label = "Password",
            error = passError,
            placeholder = "Enter your password",
            onValueChange = onPasswordChange,
            visualTransformation = if (isVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Go
            ),
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = "Password")
            },
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
            setError = { password.isNotEmpty() }
        )


        TextButton(onClick = onForgotPasswordClick, modifier = Modifier.align(Alignment.End)) {
            Text(text = "Forgot Password?")
        }
    }
}

@Composable
fun LoginFooter(
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    setError: (Boolean) -> Unit,
    login: LoginData,
    onFacebookClick: () -> Unit,
    onGoogleClick: () -> Unit,
//    email: String,
//    password: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = {
                if (login.email.isNotEmpty() && login.password.isNotEmpty()) {
                    onSignInClick()
                } else {
                    setError(true)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Log In")
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Image(painter = painterResource(id = R.drawable.google),
                contentDescription = "Google",
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        onGoogleClick()
                    }
            )
            Spacer(modifier = Modifier.height(30.dp))
            Image(painter = painterResource(id = R.drawable.facebook),
                contentDescription = "Facebook",
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        onFacebookClick()
                    }
            )
        }
        TextButton(onClick = onSignUpClick) {
            Text(text = "Don't have an account? click here")
        }
    }
}

@Composable
fun TextField(
    value: String,
    label: String,
    error: Boolean,
    placeholder: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit,
    setError: (Boolean) -> Unit
) {
    setError(value.isEmpty())
    var isVisible by remember { mutableStateOf(false) }

//    fun String.isPasswordValid(password: String): Boolean {
//        return Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$")
//            .matches(password)
//    }

    OutlinedTextField(
        value = value,
        isError = error,
        onValueChange = onValueChange,
        label = {
            Text(text = label)
        },
        placeholder = {
            Text(text = placeholder)
        },
        visualTransformation = visualTransformation,//if (isVisible) VisualTransformation.None
        //else PasswordVisualTransformation('\u2022'),
        keyboardOptions = keyboardOptions,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon
//        trailingIcon = {
//            IconButton(
//                onClick = {}
//            ) {
//                Icon(
//                    painter = if (isVisible) painterResource(Icons.Filled.Visibility)
//                            else painterResource(Icons.Filled.VisibilityOff),
//                    contentDescription = if (isVisible) "Hide password"
//                            else "Show password"
//                )
//            }
//        }
    )
    if (error) {
        Text(text = "Wrong", color = Color.Red)
    }
}