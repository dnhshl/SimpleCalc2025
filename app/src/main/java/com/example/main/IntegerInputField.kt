package com.example.main

import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun IntegerInputField(
    value: String,
    onInputChanged: (String) -> Unit
) {

    TextField(
        value = value,
        onValueChange = {
            if (it.isEmpty() || it.toIntOrNull() != null) {
                onInputChanged(it)
            }
        },
        singleLine = true,
        textStyle = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.width(100.dp)
    )
}