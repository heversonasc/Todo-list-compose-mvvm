package com.hasc.todolist.ui.feature.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hasc.todolist.data.TodoDatabaseProvider
import com.hasc.todolist.data.TodoRepositoryImpl
import com.hasc.todolist.domain.Todo
import com.hasc.todolist.domain.tod2
import com.hasc.todolist.domain.todo1
import com.hasc.todolist.domain.todo3
import com.hasc.todolist.navigation.AddEditRoute
import com.hasc.todolist.ui.UiEvent
import com.hasc.todolist.ui.componets.TodoItem
import com.hasc.todolist.ui.feature.addedit.AddEditViewModel
import com.hasc.todolist.ui.theme.TodoListTheme

@Composable
fun  ListScreen (
    navigationToAddEditScreen: (id: Long?) -> Unit,
) {
    val context = LocalContext.current.applicationContext
    val database = TodoDatabaseProvider.provide(context)
    val repository = TodoRepositoryImpl(
        dao = database.todoDao
    )
    val viewModel = viewModel<ListViewModel>{
        ListViewModel(repository = repository)

    }

    val todos by viewModel.todos.collectAsState()

    LaunchedEffect (Unit) {
        viewModel.uiEvent.collect {uiEvent ->
            when (uiEvent) {
                is UiEvent.Navigate<*> -> {
                    when (uiEvent.route) {
                        is AddEditRoute -> {
                            navigationToAddEditScreen(uiEvent.route.id)
                        }
                    }
                }
                is UiEvent.NavigateBack ->  {

                }
                is UiEvent.ShowSnackbar -> {

                }
            }
        }
    }


    ListContent(todos = todos,
        onEvent = viewModel::onEvent,
    )

}


@Composable
fun ListContent (
    todos:List<Todo>,
    onEvent: (ListEvent) -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onEvent(ListEvent.AddEdit(null)) }) {
                Icon(Icons.Default.Add, contentDescription = "Add")

            }
        }
    ) {paddingValues ->
        LazyColumn (
            modifier = Modifier
                .consumeWindowInsets(paddingValues),
            contentPadding = PaddingValues(16.dp)
        ) {
            itemsIndexed(todos) { index,todo ->
                TodoItem(
                    todo = todo,
                    onCompletedChange = {
                        onEvent(ListEvent.CompleteChanges(todo.id, it))
                    },
                    onItemClicked = {
                        onEvent(ListEvent.AddEdit(todo.id))},
                    onDeleteClick = {
                        onEvent(ListEvent.Delete(todo.id))
                    },
                )

                if (index < todos.lastIndex) {
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}

@Preview
@Composable
private fun ListContentPreview() {
    TodoListTheme {
        ListContent(
            todos = listOf(
                todo1,
                tod2,
                todo3,
            ),
            onEvent = {},

        )
    }
}
