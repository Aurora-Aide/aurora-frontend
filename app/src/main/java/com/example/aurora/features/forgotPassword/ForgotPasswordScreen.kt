package com.example.aurora.features.forgotPassword

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
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aurora.R
import com.example.aurora.navigation.Routes.MainRoute.Login.toLogIn
import com.example.aurora.ui.components.ErrorBanner
import com.example.aurora.ui.theme.*

@Composable
fun ForgotPasswordScreen(navController: NavController,
                data: ForgotPassData,
                onEmailChange: (String) -> Unit,
                onPasswordChange: (String) -> Unit,
                onRepeatChange: (String) -> Unit,
                onSendClick: () -> Unit,
                onResetClick: () -> Unit,
                isResetSuccessful: () -> Unit,
                onBackClick:() -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (data.isFirstStep && !data.resultPageGood && !data.resultPageBad) {
            ForgotPassHeader(true, onBackClick)
            Spacer(modifier = Modifier.height(30.dp))
            ForgotPassFields(
                data,
                onEmailChange = {
                    onEmailChange(it)
                }
            )
            Spacer(modifier = Modifier.height(30.dp))
            ForgotPassFooter(
                onSendClick = onSendClick,
                enabled = data.email.isNotEmpty()
            )
        } else if (!data.isFirstStep && !data.resultPageGood && !data.resultPageBad) {
            ResetPassHeader()
            Spacer(modifier = Modifier.height(30.dp))
            ResetPassFields(
                data,
                onPasswordChange = {
                    onPasswordChange(it)
                },
                onRepeatChange = {
                    onRepeatChange(it)
                })
            Spacer(modifier = Modifier.height(30.dp))
            ResetPassFooter(
                onResetClick,
                enabled = data.password.isNotEmpty() && data.repeat.isNotEmpty() && data.password == data.repeat
            )
        } else if (data.resultPageGood) {
            Success(navController)
        } else {
            Error(navController)
        }
    }
}

@Composable
fun ForgotPassHeader(isBackVisible: Boolean, onBackClick:() -> Unit) {
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
                    .clickable{
                        onBackClick()
                    }
            )
        }

    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = R.drawable.aurora),
            contentDescription = "aurora",
            modifier = Modifier
                .width(170.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Forgot Password",
            fontSize = FontSize.HEADING2,
            fontWeight = FontWeight.HEADING2,
            lineHeight = LineHeight.HEADING2,
            color = primary1)
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.width(220.dp),
            horizontalArrangement = Arrangement.Center

        ){
            Text(text = "Enter your email, so we can send you a link",
                fontSize = FontSize.PARAGRAPH2,
                fontWeight = FontWeight.PARAGRAPH2M,
                lineHeight =  LineHeight.PARAGRAPH2,
                color = primary1)
            }
        }

}

@Composable
fun ResetPassHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = R.drawable.aurora),
            contentDescription = "aurora",
            modifier = Modifier
                .width(170.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Reset Password",
            fontSize = FontSize.HEADING2,
            fontWeight = FontWeight.HEADING2,
            color = primary1)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Enter your new password",
            fontSize = FontSize.PARAGRAPH2,
            fontWeight = FontWeight.PARAGRAPH2M,
            lineHeight =  LineHeight.PARAGRAPH2,
            color = primary1)
    }
}

@Composable
fun ForgotPassFields(
    data: ForgotPassData,
    onEmailChange: (String) -> Unit,
) {
    Column {
        TextField(
            value = data.email,
            label = "Email",
            error = data.isEmailError != ForgotEmailErrors.NONE,
            placeholder = "Email",
            onValueChange = onEmailChange,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            errorText = data.isEmailError.value?.let { stringResource(it) } ?: ""
        )
        ErrorBanner(data.errorMessage)
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun ResetPassFields(
    data: ForgotPassData,
    onPasswordChange: (String) -> Unit,
    onRepeatChange: (String) -> Unit,
) {

    var isVisible1 by remember { mutableStateOf(false) }
    var isVisible2 by remember { mutableStateOf(false) }
    Column {
        TextField(
            value = data.password,
            label = "New Password",
            error = data.isPasswordError != ForgotPasswordErrors.NONE,
            placeholder = "New Password",
            onValueChange = onPasswordChange,
            visualTransformation = if (isVisible1) VisualTransformation.None
            else PasswordVisualTransformation('\u002A'),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Go
            ),
            trailingIcon = {
                IconButton(
                    onClick = {
                        isVisible1 = !isVisible1
                    }
                ) {
                    Image(
                        imageVector = if (isVisible1) Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff,
                        contentDescription = if (isVisible1) "Hide password"
                        else "Show password"
                    )
                }
            },
            errorText = data.isPasswordError.value?.let { stringResource(it) } ?: ""
        )
        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = data.repeat,
            label = "Repeat Password",
            error = data.isRepeatError != ForgotRepeatErrors.NONE,
            placeholder = "Repeat Password",
            onValueChange = onRepeatChange,
            visualTransformation = if (isVisible2) VisualTransformation.None
            else PasswordVisualTransformation('\u002A'),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Go
            ),
            trailingIcon = {
                IconButton(
                    onClick = {
                        isVisible2 = !isVisible2
                    }
                ) {
                    Image(
                        imageVector = if (isVisible2) Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff,
                        contentDescription = if (isVisible2) "Hide password"
                        else "Show password"
                    )
                }
            },
            errorText = data.isPasswordError.value?.let { stringResource(it) } ?: ""
        )
        ErrorBanner(data.errorMessage)
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun ForgotPassFooter(
    onSendClick: () -> Unit,
    enabled: Boolean,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = {
                onSendClick()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(8.dp),
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(containerColor = primary1)

        ) {
            Text(text = "Send Email", fontSize = FontSize.PARAGRAPH1, fontWeight = FontWeight.PARAGRAPH1M,
                lineHeight = LineHeight.PARAGRAPH1, color = base0)
        }
    }
}

@Composable
fun ResetPassFooter(
    onResetClick: () -> Unit,
    enabled: Boolean,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = {
                onResetClick()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(8.dp),
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(containerColor = primary1)

        ) {
            Text(text = "Reset Password", fontSize = FontSize.PARAGRAPH1, fontWeight = FontWeight.PARAGRAPH1M,
                lineHeight = LineHeight.PARAGRAPH1, color = base0)
        }
    }
}

@Composable
fun Success(navController: NavController){
    Column(horizontalAlignment = Alignment.CenterHorizontally){
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(shape = CircleShape)
                .background(color = functionalSuccess),
            contentAlignment = Alignment.Center
        ){
            Image(painter = painterResource(id = R.drawable.check),
                contentDescription = "check",
                modifier = Modifier
                    .width(40.dp)
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "Password Changed!",
            fontSize = FontSize.HEADING2,
            fontWeight = FontWeight.HEADING2,
            color = functionalSuccess)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Your password has been changed successfully.",
            fontSize = FontSize.PARAGRAPH1,
            fontWeight = FontWeight.PARAGRAPH1M,
            lineHeight = LineHeight.PARAGRAPH1,
            color = functionalSuccess)
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {
                navController.toLogIn()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primary1)

        ) {
            Text(text = "Back to Jigit", fontSize = FontSize.PARAGRAPH1, fontWeight = FontWeight.PARAGRAPH1M,
                lineHeight = LineHeight.PARAGRAPH1, color = base0)
        }
    }
}

@Composable
fun Error(navController: NavController){
    Column(horizontalAlignment = Alignment.CenterHorizontally){
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(shape = CircleShape)
                .background(color = functionalError),
            contentAlignment = Alignment.Center

        ){
            Image(painter = painterResource(id = R.drawable.x),
                contentDescription = "x",
                modifier = Modifier
                    .width(40.dp)
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "Password Change Unsuccessful!",
            fontSize = FontSize.HEADING2,
            fontWeight = FontWeight.HEADING2,
            color = functionalError)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "There was an error while trying to change your password.",
            fontSize = FontSize.PARAGRAPH1,
            fontWeight = FontWeight.PARAGRAPH1M,
            lineHeight = LineHeight.PARAGRAPH1,
            color = functionalError)
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {
                navController.toLogIn()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primary1)

        ) {
            Text(text = "Back to Jigit", fontSize = FontSize.PARAGRAPH1, fontWeight = FontWeight.PARAGRAPH1M,
                lineHeight = LineHeight.PARAGRAPH1, color = base0)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextField(
    value: String,
    label: String,
    placeholder: String,
    error: Boolean,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    trailingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit,
    errorText: String,
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
        modifier =Modifier.fillMaxWidth(),
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