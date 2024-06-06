package com.nqmgaming.todoapplab.presentation.add_todo.components

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nqmgaming.todoapplab.R
import com.nqmgaming.todoapplab.core.common.Priority
import com.nqmgaming.todoapplab.core.components.MyDatePickerDialog
import com.nqmgaming.todoapplab.core.components.TopAppBar
import com.nqmgaming.todoapplab.core.utils.ConvertMillisToDate.convertMillisToDate
import com.nqmgaming.todoapplab.presentation.add_todo.AddTodoScreenEvent
import com.nqmgaming.todoapplab.presentation.add_todo.AddTodoScreenViewModel
import kotlinx.coroutines.launch
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddTodoScreen(
    navController: NavController,
    viewModel: AddTodoScreenViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val title = state.title
    val description = state.description
    val priority = state.priority
    val dueDate = state.dueDate

    val scope = rememberCoroutineScope()

    val keyboard = LocalSoftwareKeyboardController.current

    var showDatePicker by remember {
        mutableStateOf(false)
    }
    // Dropdown Menu select priority
    var expanded by remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            TopAppBar(
                title = "Add Todo",
                onCancelClick = {
                    keyboard?.hide()
                    navController.navigateUp()
                },
                onSaveClick = {
                    scope.launch {
                        keyboard?.hide()
                        viewModel.onEvent(AddTodoScreenEvent.SaveButtonClicked)
                        navController.navigateUp()
                    }
                },
                isEnableSave = title.isNotEmpty()
            )
        },
        modifier = Modifier
            .fillMaxSize()

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding()),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                TextField(
                    value = title,
                    onValueChange = {
                        scope.launch {
                            viewModel.onEvent(AddTodoScreenEvent.TitleChanged(it))
                        }
                    },
                    placeholder = { Text("Title") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Gray,
                        unfocusedIndicatorColor = Color.Gray,
                        cursorColor = Color.Black,
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true
                )
                TextField(
                    value = description,
                    onValueChange = {
                        scope.launch {
                            viewModel.onEvent(AddTodoScreenEvent.DescriptionChanged(it))
                        }
                    },
                    placeholder = { Text("Add Notes") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .align(Alignment.Start),
                    maxLines = 8,
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black,

                        ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    )
                )

            }

            TextField(
                value = if (dueDate.time.toString()
                        .isNotEmpty()
                ) convertMillisToDate(dueDate.time) else "",
                onValueChange = {
                    scope.launch {
                        viewModel.onEvent(AddTodoScreenEvent.DueDateChanged(dueDate))
                    }
                },
                label = {
                    Text(
                        text = "Choose date", style = TextStyle(
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable {
                        showDatePicker = true
                    },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black,
                    focusedContainerColor = Color.Transparent,
                    disabledTextColor = Color.Black
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = "Calendar Icon",
                        tint = if (dueDate.time.toString()
                                .isNotEmpty()
                        ) Color.Black else Color.Gray
                    )
                },
                enabled = false,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    OutlinedTextField(
                        value = priority.name,
                        onValueChange = {
                            scope.launch {
                                viewModel.onEvent(AddTodoScreenEvent.PriorityChanged(priority))
                            }
                        },
                        placeholder = {
                            Text(
                                text = "Priority", style = TextStyle(
                                    color = Color.Gray,
                                    fontSize = 16.sp
                                )
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = Color.Black,
                            disabledTextColor = MaterialTheme.colorScheme.onSurface
                        ),
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    expanded = true
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.ArrowDropDown,
                                    contentDescription = "Priority Dropdown"
                                )
                            }
                        },
                        enabled = false
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .align(alignment = Alignment.CenterHorizontally)
                    ) {
                        Priority.entries.forEach { priority ->
                            DropdownMenuItem(
                                onClick = {
                                    scope.launch {
                                        viewModel.onEvent(
                                            AddTodoScreenEvent.PriorityChanged(
                                                priority
                                            )
                                        )
                                    }
                                    expanded = false
                                },
                                text = {
                                    Text(text = priority.name)
                                }
                            )
                        }
                    }
                }
            }

            if (showDatePicker) {
                MyDatePickerDialog(
                    onDateSelected = { date ->
                        scope.launch {
                            viewModel.onEvent(AddTodoScreenEvent.DueDateChanged(date))
                        }
                    },
                    onDismiss = { showDatePicker = false }
                )
            }
        }
    }
}
