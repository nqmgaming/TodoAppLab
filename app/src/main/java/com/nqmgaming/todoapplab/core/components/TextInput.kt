package com.nqmgaming.todoapplab.core.components

import androidx.compose.foundation.clickable
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
    keyboardOptions: KeyboardOptions,
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {

    TextField(
        value = title,
        onValueChange = {
            onChangeText(it)
        },
        placeholder = { Text(text = placeholder) },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .clickable { onClick?.invoke() },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Gray,
            unfocusedIndicatorColor = Color.Gray,
            cursorColor = Color.Black,
        ),
        keyboardOptions = keyboardOptions,
        singleLine = oneLine,
        maxLines = maxLine,
        enabled = enabled,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon
    )
}