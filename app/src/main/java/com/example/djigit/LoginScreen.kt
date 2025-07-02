package com.example.djigit

import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.djigit.Routes.MainRoute.ForgotPassword.toForgotPassword
import com.example.djigit.Routes.MainRoute.Home.toHome
import com.example.djigit.Routes.MainRoute.SignUp.toSignUp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun LoginScreen(navController: NavController) {
     val scrollState = rememberScrollState()

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
                    val email = remember { mutableStateOf("") }
                    val password = remember { mutableStateOf("") }
                    val response = remember { mutableStateOf("") }
                    val emailError = remember { mutableStateOf(false) }
                    val passError = remember { mutableStateOf(false) }


                    LoginHeader()
                    Spacer(modifier = Modifier.height(20.dp))
                    LoginFields(
                         email.value,
                         password.value,
                         emailError = emailError.value,
                         passError = passError.value,
                         onEmailChange = { email.value = it},
                         onPasswordChange = { password.value = it },
                         onForgotPasswordClick = { navController.toForgotPassword() }
                    )
                    LoginFooter(
                         onSignInClick = {
                              postLoginData(context, email.value, password.value, response)
                              if(response.value == "Wrong password"){
                                   passError.value = true
                              } else if(response.value == "Invalid email") {
                                   emailError.value = true
                              } else {
                                   // create token ???
                                   navController.toHome()
                              }
                         },
                         onSignUpClick = {
                              navController.toSignUp()
                         },
                         email = email.value,
                         password = password.value,
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
     email: String,
     password: String,
     emailError: Boolean,
     passError: Boolean,
     onEmailChange: (String) -> Unit,
     onPasswordChange: (String) -> Unit,
     onForgotPasswordClick: () -> Unit
) {
     Column {
          TextField(
               value = email,
               label = "Email",
               error = emailError,
               placeholder = "Enter your email",
               onValueChange = onEmailChange,
               leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = "Email")
               },
               keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
               ),
               setError = {email.isEmpty()}
          )

          Spacer(modifier = Modifier.height(10.dp))

          TextField(
               value = password,
               label = "Password",
               error = passError,
               placeholder = "Enter your password",
               onValueChange = onPasswordChange,
               visualTransformation = PasswordVisualTransformation(),
               keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Go
               ),
               leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = "Password")
               },
               setError = {password.isEmpty()}
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
     email: String,
     password: String
) {
     Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Button(onClick = {
               if(email.isNotEmpty() && password.isNotEmpty()){
                    onSignInClick()
               }
               else{
                    setError(true)
               }
          },
          modifier = Modifier.fillMaxWidth()) {
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
                         .clickable {}
               )
               Spacer(modifier = Modifier.height(30.dp))
               Image(painter = painterResource(id = R.drawable.facebook),
                    contentDescription = "Facebook",
                    modifier = Modifier
                         .size(50.dp)
                         .clickable {}
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
     error: Boolean ,
     placeholder: String,
     visualTransformation: VisualTransformation = VisualTransformation.None,
     keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
     leadingIcon: @Composable (() -> Unit)? = null,
     trailingIcon: @Composable (() -> Unit)? = null,
     onValueChange: (String) -> Unit,
     setError: (Boolean) -> Unit
) {
     setError(value.isEmpty())

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
          visualTransformation = visualTransformation,
          keyboardOptions = keyboardOptions,
          leadingIcon = leadingIcon,
          trailingIcon = trailingIcon
     )
     if (error) {
          Text(text = "Wrong", color = Color.Red)
     }
}