package com.survivalcoding.gangnam2kiandroidstudy.presentation.counter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CounterRoot(
    viewModel: CounterViewModel = viewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    CounterScreen(
        state = state,
        onIncrement = viewModel::increment,
        onDecrement = viewModel::decrement,
    )
}
