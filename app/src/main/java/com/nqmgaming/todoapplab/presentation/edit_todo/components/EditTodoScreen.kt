package com.nqmgaming.todoapplab.presentation.edit_todo.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.nqmgaming.todoapplab.presentation.edit_todo.EditTodoScreenEvent
import com.nqmgaming.todoapplab.presentation.edit_todo.EditTodoScreenViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun EditTodoScreen(
    navController: NavController,
    viewModel: EditTodoScreenViewModel = hiltViewModel()
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

    LaunchedEffect(key1 = true) {
        viewModel.state
        Log.d("EditTodoScreen", "EditTodoScreen: $state")
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = "Edit Todo",
                onCancelClick = {
                    keyboard?.hide()
                    navController.navigateUp()
                },
                onSaveClick = {
                    scope.launch (Dispatchers.Main){
                        keyboard?.hide()
                        viewModel.onEvent(EditTodoScreenEvent.SaveButtonClicked)
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
                TextInput(
                    title = title,
                    onChangeText = {
                        scope.launch {
                            viewModel.onEvent(EditTodoScreenEvent.TitleChanged(it))
                        }
                    },
                    oneLine = true,
                    placeholder = "Add Title",
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                )

                TextInput(
                    title = description,
                    onChangeText = {
                        scope.launch {
                            viewModel.onEvent(EditTodoScreenEvent.DescriptionChanged(it))
                        }
                    },
                    oneLine = false,
                    maxLine = 8,
                    placeholder = "Add Description",
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.height(200.dp)
                )

            }

            TextInput(
                title = if (dueDate.time.toString()
                        .isNotEmpty()
                ) convertMillisToDate(dueDate.time) else "",
                onChangeText = {
                    scope.launch {
                        viewModel.onEvent(EditTodoScreenEvent.DueDateChanged(dueDate))
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
                        viewModel.onEvent(EditTodoScreenEvent.PriorityChanged(newPriority))
                    }
                },
                expanded = expanded,
                onExpandedChange = { expanded = it }
            )

            if (showDatePicker) {
                MyDatePickerDialog(
                    onDateSelected = { date ->
                        scope.launch {
                            viewModel.onEvent(EditTodoScreenEvent.DueDateChanged(date))
                        }
                    },
                    onDismiss = { showDatePicker = false }
                )
            }
        }
    }
}

