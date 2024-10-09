package com.hasc.todolist.ui.feature.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hasc.todolist.data.TodoRepository
import com.hasc.todolist.navigation.AddEditRoute
import com.hasc.todolist.ui.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ListViewModel(
    private val repository: TodoRepository): ViewModel() {

       val todos = repository.getAll()
           .stateIn(
               scope = viewModelScope,
               started = SharingStarted.WhileSubscribed(5000),
               initialValue = emptyList()
           )

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent (event: ListEvent) {

        when(event) {
            is ListEvent.Delete -> {
                delete(event.id)
            }
            is ListEvent.CompleteChanges -> {
                completedChanged(event.id, event.isCompleted)
            }
            is ListEvent.AddEdit -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(AddEditRoute(event.id)))
                }
            }
        }
    }

    private fun delete (id: Long) {
        viewModelScope.launch {
            repository.delete(id)
        }
    }

    private fun completedChanged(id: Long, completed: Boolean) {
        viewModelScope.launch {
            repository.updateCompleted(id, completed)
        }
    }
}