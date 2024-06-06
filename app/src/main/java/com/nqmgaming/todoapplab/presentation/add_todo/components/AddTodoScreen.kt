package com.nqmgaming.todoapplab.presentation.add_todo.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nqmgaming.todoapplab.R
import com.nqmgaming.todoapplab.core.components.MyDatePickerDialog
import com.nqmgaming.todoapplab.core.components.PrioritySelector
import com.nqmgaming.todoapplab.core.components.TextInput
import com.nqmgaming.todoapplab.core.components.TopAppBar
import com.nqmgaming.todoapplab.core.utils.ConvertMillisToDate.convertMillisToDate
import com.nqmgaming.todoapplab.presentation.add_todo.AddTodoScreenEvent
import com.nqmgaming.todoapplab.presentation.add_todo.AddTodoScreenViewModel
import kotlinx.coroutines.Dispatchers
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
                    scope.launch(Dispatchers.Main) {
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

            TextInput(
                title = if (dueDate.time.toString()
                        .isNotEmpty()
                ) convertMillisToDate(dueDate.time) else "",
                onChangeText = {
                    scope.launch {
                        viewModel.onEvent(AddTodoScreenEvent.DueDateChanged(dueDate))
                    }
                },
                oneLine = true,
                placeholder = "Choose date",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                onClick = { showDatePicker = true },
                enabled = false,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = "Calendar Icon"
                    )
                },
                modifier = Modifier.padding(6.dp)
            )

            PrioritySelector(
                priority = state.priority,
                onPriorityChange = { newPriority ->
                    scope.launch {
                        viewModel.onEvent(AddTodoScreenEvent.PriorityChanged(newPriority))
                    }
                },
                expanded = expanded,
                onExpandedChange = { expanded = it }
            )

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
