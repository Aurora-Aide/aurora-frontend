package com.example.aurora.features.admin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.aurora.R
import com.example.aurora.features.home.Back
import com.example.aurora.features.login.TextField
import com.example.aurora.ui.components.ErrorBanner
import com.example.aurora.ui.theme.FontSize
import com.example.aurora.ui.theme.FontWeight
import com.example.aurora.ui.theme.LineHeight
import com.example.aurora.ui.theme.base0
import com.example.aurora.ui.theme.primary1
import com.example.aurora.ui.theme.secondary2
import com.example.aurora.ui.theme.secondary4

@Composable
fun AdminCreateDispenserModelScreen(
    modelData: AdminCreateDispenserModelData,
    onCodeChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onSlotCountChange: (String) -> Unit,
    onSerialPrefixChange: (String) -> Unit,
    onAddModel: () -> Unit,
    onBackClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    
    LaunchedEffect(modelData.isSuccess) {
        if (modelData.isSuccess) onBackClick()
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
            Spacer(modifier = Modifier.weight(0.3f))
            Text(
                text = stringResource(R.string.create_dispenser_model),
                color = primary1,
                fontSize = FontSize.HEADING3,
                fontWeight = FontWeight.HEADING3,
                lineHeight = LineHeight.HEADING3
            )
            Spacer(modifier = Modifier.weight(0.2f))
            AddModelFields(
                modelData = modelData,
                onCodeChange = onCodeChange,
                onNameChange = onNameChange,
                onSlotCountChange = onSlotCountChange,
                onSerialPrefixChange = onSerialPrefixChange
            )
            Spacer(modifier = Modifier.weight(0.1f))
            AddModelFooter(
                model = modelData,
                onAddDispenserClick = onAddModel
            )
            Spacer(modifier = Modifier.weight(0.3f))
        }
    }
}

@Composable
fun AddModelFields(
    modelData: AdminCreateDispenserModelData,
    onCodeChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onSlotCountChange: (String) -> Unit,
    onSerialPrefixChange: (String) -> Unit,
){
    Column(

    ) {
        TextField(
            value = modelData.code,
            onValueChange = onCodeChange,
            label = stringResource(R.string.code),
            error = modelData.isCodeError != AddModelCodeErrors.NONE,
            placeholder = stringResource(R.string.code),
            errorText = modelData.isCodeError.value?.let { stringResource(it) } ?: ""
        )
        Spacer(modifier = Modifier.height(12.dp))
        TextField(
            value = modelData.name,
            onValueChange = onNameChange,
            label = stringResource(R.string.name),
            placeholder = stringResource(R.string.name),
            error = modelData.isNameError != AddModelNameErrors.NONE,
            errorText = modelData.isNameError.value?.let { stringResource(it) } ?: ""
        )
        Spacer(modifier = Modifier.height(12.dp))
        TextField(
            value = modelData.slotCount.toString(),
            onValueChange = onSlotCountChange,
            label = stringResource(R.string.slot_count),
            placeholder = stringResource(R.string.slot_count),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            error = modelData.isSlotCountError != AddModelSlotCountErrors.NONE,
            errorText = modelData.isSlotCountError.value?.let { stringResource(it) } ?: ""
        )
        Spacer(modifier = Modifier.height(12.dp))
        TextField(
            value = modelData.serialPrefix,
            onValueChange = onSerialPrefixChange,
            label = stringResource(R.string.serial_prefix),
            placeholder = stringResource(R.string.serial_prefix),
            error = modelData.isSerialPrefixError != AddModelSerialPrefixErrors.NONE,
            errorText = modelData.isSerialPrefixError.value?.let { stringResource(it) } ?: ""
        )
        ErrorBanner(modelData.errorMessage)
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun AddModelFooter(
    model: AdminCreateDispenserModelData,
    onAddDispenserClick: () -> Unit,
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
            enabled = !model.isLoading,
            colors = ButtonDefaults.buttonColors(containerColor = primary1)

        ) {
            Text(
                text = if (model.isLoading) {
                    stringResource(R.string.adding_model)
                } else {
                    stringResource(R.string.add_model)
                },
                fontSize = FontSize.PARAGRAPH1,
                fontWeight = FontWeight.PARAGRAPH1M,
                lineHeight = LineHeight.PARAGRAPH1,
                color = base0
            )
            Image(
                painter = painterResource(id = R.drawable.add_circle),
                contentDescription = "addModel",
                modifier = Modifier
                    .size(20.dp)
            )
        }
    }
}