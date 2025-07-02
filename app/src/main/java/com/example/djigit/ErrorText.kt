package com.example.djigit

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ErrorText(
//    value: TextFieldValue,
//    onValueChange: (TextFieldValue) -> Unit,
//    modifier: Modifier = Modifier,
//    enabled: Boolean = true,
//    readOnly: Boolean = false,
//    //textStyle: TextStyle = LocalTextStyle.current,
//    //label: @Composable (() -> Unit)? = null,
//    //placeholder: @Composable (() -> Unit)? = null,
//    //leadingIcon: @Composable (() -> Unit)? = null,
//    //trailingIcon: @Composable (() -> Unit)? = null,
//    //helperMessage: @Composable (() -> Unit)? = null,
//    errorMessage: @Composable (() -> Unit)? = null,
//    isError: Boolean = false,
//    //visualTransformation: VisualTransformation = VisualTransformation.None,
//    //: KeyboardOptions = KeyboardOptions.Default,
//    //keyboardActions: KeyboardActions = KeyboardActions(),
//    //singleLine: Boolean = false,
//    //maxLines: Int = Int.MAX_VALUE,
//    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
////    shape: Shape =
////        MaterialTheme.shapes.small.copy(bottomEnd = ZeroCornerSize, bottomStart = ZeroCornerSize),
//    colors: TextFieldColors = TextFieldDefaults.textFieldColors()
//) {
//    Column {
//        ErrorText(
//            value = value,
//            onValueChange = onValueChange,
//            modifier = modifier,
//            enabled = enabled,
//            readOnly = readOnly,
//            textStyle = textStyle,
//            label = label,
//            placeholder = placeholder,
//            leadingIcon = leadingIcon,
//            trailingIcon = trailingIcon,
//            isError = isError,
//            visualTransformation = visualTransformation,
//            keyboardOptions = keyboardOptions,
//            keyboardActions = keyboardActions,
//            singleLine = singleLine,
//            maxLines = maxLines,
//            interactionSource = interactionSource,
//            shape = shape,
//            colors = colors
//        )
//        Box(
//            modifier = Modifier
//                .requiredHeight(16.dp)
//                .padding(start = 16.dp, end = 12.dp)
//        ) {
//            if (isError) {
//                if (errorMessage != null) {
//                    errorMessage()
//                }
//            } else {
//                if (helperMessage != null) {
//                    helperMessage()
//                }
//            }
//        }
//    }
//}