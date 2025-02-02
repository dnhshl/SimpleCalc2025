package com.example.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// Data class representing the UI state
data class MyUiState(
    val entry1: String = "",
    val entry2: String = "",
    val result: String = ""
)

// ViewModel using StateFlow with a single UI state object
class MainViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MyUiState())
    val uiState: StateFlow<MyUiState> get() = _uiState

    fun onEntry1Changed(newValue: String) {
        if (newValue.isEmpty() || newValue.toIntOrNull() != null) {
            _uiState.value = _uiState.value.copy(entry1 = newValue)
        }
    }

    fun onEntry2Changed(newValue: String) {
        if (newValue.isEmpty() || newValue.toIntOrNull() != null) {
            _uiState.value = _uiState.value.copy(entry2 = newValue)
        }
    }

    fun computeResult() {
        val i = _uiState.value.entry1.toIntOrNull()
        val j = _uiState.value.entry2.toIntOrNull()
        if (i != null && j != null) {
            _uiState.value = _uiState.value.copy(result = (i + j).toString())
        }
    }
}
