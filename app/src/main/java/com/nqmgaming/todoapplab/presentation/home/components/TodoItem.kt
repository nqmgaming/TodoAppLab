package com.nqmgaming.todoapplab.presentation.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nqmgaming.todoapplab.core.common.Priority
import com.nqmgaming.todoapplab.domain.model.Todo
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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