package com.example.main

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

// Data class representing the UI state
@Serializable
data class MyUiState(
    val entry1: String = "",
    val entry2: String = "",
    val result: String = ""
)


private val Context.dataStore by preferencesDataStore(name = "ui_state")

// ViewModel using StateFlow with a single UI state object
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val dataStore = application.dataStore
    private val UI_STATE_KEY = stringPreferencesKey("ui_state")

    private val _uiState = MutableStateFlow(MyUiState())
    val uiState: StateFlow<MyUiState> get() = _uiState

    init {
        // Load persisted UI state
        viewModelScope.launch {
            getUiState().collect { persistedState ->
                _uiState.value = persistedState
            }
            Log.i(">>>>>", "loading Preferences: ${_uiState.value}")
        }
    }

    fun onEntry1Changed(newValue: String) {
        if (newValue.isEmpty() || newValue.toIntOrNull() != null) {
            _uiState.update { it.copy(entry1 = newValue) }
            saveUiState()
        }
    }

    fun onEntry2Changed(newValue: String) {
        if (newValue.isEmpty() || newValue.toIntOrNull() != null) {
            _uiState.update { it.copy(entry2 = newValue) }
            saveUiState()
        }
    }

    fun computeResult() {
        val i = _uiState.value.entry1.toIntOrNull()
        val j = _uiState.value.entry2.toIntOrNull()
        if (i != null && j != null) {
            _uiState.update { it.copy(result = (i + j).toString()) }
            saveUiState()
        }
    }


    // Funktionen, um den UI-Zustand persistent zu speichern und wiederherzustellen
    // ------------------------------------------------------------------------------

    private fun getUiState(): Flow<MyUiState> =
        dataStore.data.map { preferences ->
            preferences[UI_STATE_KEY]?.let { jsonString ->
                try {
                    Json.decodeFromString(MyUiState.serializer(), jsonString)
                } catch (e: Exception) {
                    MyUiState() // Fallback if parsing fails
                }
            } ?: MyUiState() // Default if no value stored
        }


    private fun saveUiState() {
        val state = _uiState.value
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[UI_STATE_KEY] = Json.encodeToString(MyUiState.serializer(), state)
            }
        }
    }
}
