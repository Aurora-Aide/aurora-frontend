package com.example.aurora.features.home

import android.service.carrier.MessagePdu
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.aurora.R
import com.example.aurora.features.dispenser.ScheduleDetailData
import com.example.aurora.ui.theme.FontSize
import com.example.aurora.ui.theme.FontWeight
import com.example.aurora.ui.theme.LineHeight
import com.example.aurora.ui.theme.base0
import com.example.aurora.ui.theme.functionalError
import com.example.aurora.ui.theme.primary1
import com.example.aurora.ui.theme.secondary2

@Composable
fun AddDispenserScreen(
    dispenser: DispenserData,
    onIDChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onAddDispenserClick: () -> Unit,
    isAddDispenserSuccessful: () -> Unit,
    onBackClick: () -> Unit,
) {
    val scrollState = rememberScrollState()

    if (dispenser.isAddDispenserSuccessful) {
        LaunchedEffect(null) {
            isAddDispenserSuccessful()
        }
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp, 16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Back(onBackClick = { onBackClick() })
            Spacer(modifier = Modifier.weight(1f))
            AddDispenserHeader()
            Spacer(modifier = Modifier.height(30.dp))
            AddDispenserFields(
                dispenser,
                onIDChange = {
                    onIDChange(it)
                },
                onNameChange = {
                    onNameChange(it)
                },
            )
//        if (dispenser.isCountError) {
//            Spacer(modifier = Modifier.height(12.dp))
//            Text(
//                text = "You've exceeded the maximum allowed dispensers!" +
//                        "You have 5 dispensers which is the maximum!",
//                color = functionalError,
//                fontSize = FontSize.PARAGRAPH2,
//                fontWeight = FontWeight.PARAGRAPH2M
//            )
//        }
            Spacer(modifier = Modifier.weight(1f))
            AddDispenserFooter(
                dispenser = dispenser,
                onAddDispenserClick = {
                    onAddDispenserClick()
                },
                enabled = dispenser.id.isNotEmpty() &&
                        dispenser.name.isNotEmpty() &&
                        !dispenser.isLoading
            )
            Spacer(modifier = Modifier.weight(0.3f))
        }
    }


}

@Composable
fun Back(
    onBackClick:() -> Unit,
){
    Row{
        Image(
            painter = painterResource(id = R.drawable.backarrow),
            contentDescription = "back",
            modifier = Modifier
                .size(28.dp)
                .clickable { onBackClick() }
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun AddDispenserHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.aurora),
            contentDescription = "aurora",
            modifier = Modifier
                .width(170.dp)
        )
        Spacer(modifier = Modifier.height(36.dp))
        Text(
            text = "Add your dispenser:",
            fontSize = FontSize.HEADING2,
            fontWeight = FontWeight.HEADING2,
            lineHeight = LineHeight.HEADING2,
            color = primary1
        )
    }
}

@Composable
fun AddDispenserFields(
    dispenser: DispenserData,
    onIDChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
) {
    Column {
        TextField(
            value = dispenser.id,
            label = "Dispenser ID*",
            error = dispenser.isIDError != AddDispenserIDErrors.NONE,
            placeholder = "Dispenser ID*",
            onValueChange = onIDChange,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            errorText = dispenser.isIDError.value?.let { stringResource(it) } ?: "",
            //enabled = !dispenser.isCountError
        )

        Spacer(modifier = Modifier.height((10.dp)))

        Text(
            modifier = Modifier.padding(start = 28.dp),
            text = "*To find the ID of your dispenser look at the back of its box",
            fontSize = FontSize.PARAGRAPH3,
            fontWeight = FontWeight.PARAGRAPH3R,
            lineHeight = LineHeight.PARAGRAPH3,
            color = secondary2
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = dispenser.name,
            label = "Name",
            error = dispenser.isNameError != AddDispenserNameErrors.NONE,
            placeholder = "Name",
            onValueChange = onNameChange,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            errorText = dispenser.isNameError.value?.let { stringResource(it) } ?: "",
            //enabled = !dispenser.isCountError
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun AddDispenserFooter(
    dispenser: DispenserData,
    onAddDispenserClick: () -> Unit,
    enabled: Boolean,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = {
                onAddDispenserClick()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(8.dp),
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(containerColor = primary1)

        ) {
            Text(
                text = if (dispenser.isLoading) "Adding Dispenser...  " else "Add Dispenser  ",
                fontSize = FontSize.PARAGRAPH1,
                fontWeight = FontWeight.PARAGRAPH1M,
                lineHeight = LineHeight.PARAGRAPH1,
                color = base0
            )
            Image(
                painter = painterResource(id = R.drawable.add_circle),
                contentDescription = "add_dispenser",
                modifier = Modifier
                    .size(20.dp)
            )
        }
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
    //enabled: Boolean
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
        //enabled = enabled,
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