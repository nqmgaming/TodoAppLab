package com.nqmgaming.todoapplab.core.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit,
    isEnableSave: Boolean
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        },
        navigationIcon = {
            TextButton(
                onClick = {
                    onCancelClick()
                }
            ) {
                Text(
                    text = "Cancel",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 16.sp,
                    ),
                )
            }
        },
        actions = {
            TextButton(
                onClick = {
                    onSaveClick()
                },
                enabled = isEnableSave
            ) {
                Text(
                    text = "Save",
                    style = MaterialTheme.typography.titleSmall
                        .copy(
                            fontSize = 16.sp,
                        )
                )
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}