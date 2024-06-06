package com.nqmgaming.todoapplab.presentation.home

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.nqmgaming.todoapplab.R
import com.nqmgaming.todoapplab.core.common.Priority
import com.nqmgaming.todoapplab.domain.model.Todo
import com.nqmgaming.todoapplab.presentation.Screen
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {

    val currentDate = SimpleDateFormat("MMM dd", Locale.getDefault()).format(Date())
    Log.d("HomeScreen", "Current Date: $currentDate")
    val context = LocalContext.current

    val state = viewModel.state
    val scope = rememberCoroutineScope()

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination

    LaunchedEffect(currentDestination) {
        viewModel.getTodos()
    }

    Log.d("HomeScreen", "Todos: ${state.todos}")

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = currentDate,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 19.sp,
                    ),
                )
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(32.dp)
                        .background(MaterialTheme.colorScheme.surface, CircleShape)
                        .clickable {
                            Toast
                                .makeText(context, "Search", Toast.LENGTH_SHORT)
                                .show()
                        }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(id = R.string.search),
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddTodoScreen.route)
                },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = stringResource(id = R.string.add_todo),
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Completed", style = MaterialTheme.typography.bodySmall)
                        Text(
                            text = state.todos.count { it.isCompleted }.toString(),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Pending", style = MaterialTheme.typography.bodySmall)
                        Text(
                            text = state.todos.count { !it.isCompleted }.toString(),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                ) {
                    items(state.todos.size) { index ->
                        val todo = state.todos[index]
                        TodoItem(
                            todo = todo,
                            onCheckedChange = { isChecked ->
                                scope.launch {
                                    viewModel.onEvent(
                                        HomeScreenEvent.TodoCheckedChanged(
                                            todo,
                                            isChecked
                                        )
                                    )
                                }
                            },
                            onTodoClick = {
                                navController.navigate("${Screen.EditTodoScreen.route}?todoId=${todo.id}")
                            },
                            onDelete = {
                                scope.launch {
                                    viewModel.onEvent(
                                        HomeScreenEvent.DeleteTodoClicked(
                                            todo
                                        )
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoItem(
    todo: Todo,
    onCheckedChange: (Boolean) -> Unit,
    onTodoClick: () -> Unit,
    onDelete: () -> Unit = {}
) {
    var openDialog by remember { mutableStateOf(false) }

    val colorPriority = if (todo.isCompleted) {
        when (todo.priority) {
            Priority.LOW -> Color.Gray
            Priority.MEDIUM -> Color.Yellow
            Priority.HIGH -> Color.Red
            else -> Color.Gray
        }
    } else {
        when (todo.priority) {
            Priority.LOW -> Color.Gray.copy(
                alpha = 0.5f
            )

            Priority.MEDIUM -> Color.Yellow.copy(
                alpha = 0.5f
            )

            Priority.HIGH -> Color.Red.copy(
                alpha = 0.5f
            )

            else -> Color.Gray.copy(
                alpha = 0.5f
            )
        }
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.Gray.copy(
                alpha = 0.1f
            ),
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .combinedClickable(
                onClick = onTodoClick,
                onLongClick = {
                    openDialog = true
                }
            )
    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(
                modifier = Modifier
                    .width(8.dp)
                    .height(24.dp)
                    .background(color = colorPriority, shape = CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        onCheckedChange(!todo.isCompleted)
                    }
            ) {
                Checkbox(
                    checked = todo.isCompleted,
                    onCheckedChange = { onCheckedChange(!todo.isCompleted) },
                    colors = CheckboxDefaults.colors(
                        checkmarkColor = Color.Gray,
                        uncheckedColor = Color.Red,
                        checkedColor = Color.Green,
                        disabledCheckedColor = MaterialTheme.colorScheme.onSurface,
                    )
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = todo.title,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = if (todo.isCompleted) Color.Gray else MaterialTheme.colorScheme.onSurface,
                        fontSize = 16.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textDecoration = if (todo.isCompleted) TextDecoration.LineThrough else null,
                    modifier = Modifier.fillMaxWidth()
                )

                if (todo.description?.isNotBlank() == true) {
                    Text(
                        text = todo.description,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.Gray,
                            fontSize = 13.sp
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth(),
                        textDecoration = if (todo.isCompleted) TextDecoration.LineThrough else null
                    )
                }
            }

            Text(
                text = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(todo.dueDate),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 12.sp
                )
            )
        }
    }

    if (openDialog) {
        AlertDialog(
            onDismissRequest = {
                openDialog = false
            },
            title = {
                Text(text = "Delete Todo")
            },
            text = {
                Text(text = "Are you sure you want to delete this todo?")
            },
            confirmButton = {
                Text(
                    text = "Yes",
                    modifier = Modifier.clickable {
                        onDelete()
                        openDialog = false
                    }
                )
            },
            dismissButton = {
                Text(
                    text = "No",
                    modifier = Modifier.clickable {
                        openDialog = false
                    }
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ItemTodoPreview() {
    Column {
        TodoItem(
            todo = Todo(
                title = "Title",
                description = "Description",
                dueDate = Date(),
                isCompleted = false,
                priority = Priority.LOW
            ),
            onCheckedChange = {},
            onTodoClick = {}
        )
        TodoItem(
            todo = Todo(
                title = "Title",
                description = "Description",
                dueDate = Date(),
                isCompleted = true,
                priority = Priority.MEDIUM
            ),
            onCheckedChange = {},
            onTodoClick = {}
        )
    }
}