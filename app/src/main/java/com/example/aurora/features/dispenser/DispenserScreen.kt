package com.example.aurora.features.dispenser

import androidx.compose.animation.animateBounds
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.aurora.R
import com.example.aurora.ui.theme.FontSize
import com.example.aurora.ui.theme.LineHeight
import com.example.aurora.ui.theme.FontWeight
import com.example.aurora.ui.theme.primary1
import com.example.aurora.ui.theme.secondary4

@Composable
fun DispenserScreen(
    name: String,
    id: String,
    onPillClick: () -> Unit,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
)
{
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        Arrangement.Center

    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 16.dp)
                .verticalScroll(scrollState)
        ) {
            DispenserHeader(
                name = name,
                id = id,
                onBackClick = onBackClick,
                onEditClick = onEditClick,
            )
            Spacer(modifier = Modifier.height(16.dp))

            PillItem("Pill1", onPillClick)
            PillItem("Pill2", onPillClick)
            PillItem("Pill3", onPillClick)
            PillItem("Pill4", onPillClick)
            PillItem("Pill5", onPillClick)
            PillItem("Pill6", onPillClick)
            PillItem("Pill7", onPillClick)
            PillItem("Pill8", onPillClick)
            PillItem("Pill9", onPillClick)
            PillItem("Pill10", onPillClick)
            PillItem("Pill11", onPillClick)
            PillItem("Pill12", onPillClick)
        }
    }
}

@Composable
fun DispenserHeader(
    onBackClick: () -> Unit,
    name: String,
    id: String,
    onEditClick: () -> Unit,
){

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Back(onBackClick)
        Spacer(modifier = Modifier.height(8.dp))
        Row(){
            //TODO make it actually editable
            Text(
                text = name,
                fontSize = FontSize.HEADING3,
                fontWeight = FontWeight.HEADING3,
                lineHeight = LineHeight.HEADING3,
                color = primary1
            )
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(R.drawable.pen),
                contentDescription = "pen",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onEditClick() }
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = id,
            fontSize = FontSize.PARAGRAPH2,
            fontWeight = FontWeight.PARAGRAPH2M,
            lineHeight = LineHeight.PARAGRAPH2,
            color = primary1
        )
    }
}


//@Composable
//fun EditableHeading(
//    heading: String,
//    isEditing: Boolean,
//    draft: String,
//    onEditClick: () -> Unit,
//    onDraftChange: (String) -> Unit,
//    onSaveClick: () -> Unit,
//    onCancelClick: () -> Unit,
//) {
//    Row(verticalAlignment = Alignment.CenterVertically) {
//        if (!isEditing) {
//            Text(
//                text = heading,
//
//                fontSize = FontSize.HEADING3,
//                fontWeight = FontWeight.HEADING3,
//                lineHeight = LineHeight.HEADING3,
//                color = primary1,
//                modifier = Modifier.weight(1f)
//            )
//            Image(
//                painter = painterResource(R.drawable.pen),
//                contentDescription = "pen",
//                modifier = Modifier
//                    .size(20.dp)
//                    .clickable { onEditClick() }
//            )
//        } else {
//            OutlinedTextField(
//                value = draft,
//                onValueChange = onDraftChange,
//                singleLine = true,
//                modifier = Modifier.weight(1f)
//            )
//            IconButton(onClick = onSaveClick) {
//                Icon(Icons.Default.Check, contentDescription = "Save")
//            }
//            IconButton(onClick = onCancelClick) {
//                Icon(Icons.Default.Close, contentDescription = "Cancel")
//            }
//        }
//    }
//}


@Composable
fun PillItem(text: String, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp)
            .padding(start = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            color = primary1,
            fontSize = FontSize.PARAGRAPH1,
            fontWeight = FontWeight.PARAGRAPH1M
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Edit/View",
            color = secondary4,
            fontSize = FontSize.PARAGRAPH1,
            fontWeight = FontWeight.PARAGRAPH1M
        )
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            painter = painterResource(R.drawable.angl_left),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
    }
    Image(
        painter = painterResource(R.drawable.divider_horizontal),
        contentDescription = null,
        modifier = Modifier.fillMaxWidth()
    )
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

