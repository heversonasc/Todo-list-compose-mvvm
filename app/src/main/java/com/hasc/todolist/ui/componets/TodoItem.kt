package com.hasc.todolist.ui.componets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hasc.todolist.domain.Todo
import com.hasc.todolist.domain.tod2
import com.hasc.todolist.domain.todo1
import com.hasc.todolist.ui.theme.TodoListTheme

@Composable
fun TodoItem(
    todo: Todo,
    onCompletedChange: (Boolean) -> Unit,
    onItemClicked: () -> Unit,
    onDeleteClick: () -> Unit,

    modifier: Modifier = Modifier) {
    Surface(
        onClick = onItemClicked,
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 2.dp,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline
        )
    ) {
        Row (
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = todo.isCompleted,
                onCheckedChange = onCompletedChange)

            Spacer(modifier = Modifier.width(8.dp))

            Column (
                modifier = Modifier
                    .weight(1f),
                    ) {
                Text(
                    text = todo.title,
                    style = MaterialTheme.typography.titleLarge )

                todo.description?.let {
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = todo.description,
                        style = MaterialTheme.typography.bodyLarge)
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = onDeleteClick
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )

                }

        }

    }

}

@Preview
@Composable
private fun TodoItemPreview() {
    TodoListTheme {
        TodoItem(todo = todo1,
            onCompletedChange= {},
            onItemClicked = {},
            onDeleteClick=  {},
        )
    }
}

@Preview
@Composable
private fun TodoItemCompletedPreview() {
    TodoListTheme {
        TodoItem(todo = tod2,
            onCompletedChange= {},
            onItemClicked = {},
            onDeleteClick=  {},
        )
    }
}