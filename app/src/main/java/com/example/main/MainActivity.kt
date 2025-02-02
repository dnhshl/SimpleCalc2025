package com.example.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.main.ui.theme.SimpleCalc2025Theme

class MainActivity : ComponentActivity() {
    val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleCalc2025Theme {
                MyScreen(viewModel = viewModel)
            }
        }
    }
}


@Composable
fun MyScreen(viewModel: MainViewModel) {

    val uiState by viewModel.uiState.collectAsState()


    val isValidEntry = uiState.entry1.toIntOrNull() != null && uiState.entry2.toIntOrNull() != null

    Box(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        if (!isValidEntry) {
            Text(
                text = stringResource(id = R.string.error_message),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .padding(30.dp)
            )
        }
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row (
                verticalAlignment = Alignment.CenterVertically
            ) {

                IntegerInputField(
                    value = uiState.entry1,
                    onInputChanged = { viewModel.onEntry1Changed(it) }
                )

                Spacer(modifier = Modifier.width(30.dp))

                IntegerInputField(
                    value = uiState.entry2,
                    onInputChanged = {viewModel.onEntry2Changed(it)}
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = { viewModel.computeResult() },
                enabled = isValidEntry
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = uiState.result,
                style = MaterialTheme.typography.headlineLarge)
        }
    }
}

