package com.nqmgaming.todoapplab.core.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TextInput(
    modifier: Modifier = Modifier,
    title: String,
    onChangeText: (String) -> Unit,
    oneLine: Boolean,
    maxLine: Int = 1,
    placeholder: String,
    keyboardOptions: KeyboardOptions
) {

    TextField(
        value = title,
        onValueChange = {
            onChangeText(it)
        },
        placeholder = { Text(text = placeholder) },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Gray,
            unfocusedIndicatorColor = Color.Gray,
            cursorColor = Color.Black,
        ),
        keyboardOptions = keyboardOptions,
        singleLine = oneLine,
        maxLines = maxLine
    )

}