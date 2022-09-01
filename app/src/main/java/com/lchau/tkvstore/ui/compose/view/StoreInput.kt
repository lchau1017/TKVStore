package com.lchau.tkvstore.ui.compose.view

import androidx.compose.foundation.focusable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lchau.tkvstore.R
import com.lchau.tkvstore.ui.compose.theme.DraculaBlack
import com.lchau.tkvstore.ui.compose.theme.TEXT_SIZE

@Composable
fun InputText(
    textToDisplay: String,
    modifier: Modifier,
    onTextChanged: (String) -> Unit,
    onInputSubmitted: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    TextField(
        value = textToDisplay,
        onValueChange = onTextChanged,
        placeholder = { Text(stringResource(id = R.string.command_input_hint)) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = { onInputSubmitted() }
        ),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            disabledTextColor = Color.Transparent,
            backgroundColor = DraculaBlack,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        textStyle = TextStyle.Default.copy(
            fontSize = TEXT_SIZE.sp,
        ),
        singleLine = true,
        maxLines = 1,
        shape = RoundedCornerShape(4.dp),
        modifier = modifier
            .focusable(true)
            .focusRequester(focusRequester)
    )
    LaunchedEffect(focusRequester) {
        focusRequester.requestFocus()
    }
}