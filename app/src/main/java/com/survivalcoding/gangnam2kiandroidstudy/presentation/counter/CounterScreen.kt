package com.survivalcoding.gangnam2kiandroidstudy.presentation.counter

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun CounterScreen(
    state: CounterState = CounterState(),
    onIncrement: () -> Unit = {},
    onDecrement: () -> Unit = {},
    modifier: Modifier = Modifier,
) {

    Column(modifier = modifier) {
        // 현재 카운트 값을 텍스트로 표시
        // countValue.value로 State의 실제 값에 접근
        Text(text = "Count: ${state.value}")

        // 증가 버튼
        // onClick 람다에서 ViewModel의 increment() 함수 호출
        Button(onClick = { onIncrement() }) {
            Text(text = "Increment")
        }

        // 감소 버튼
        // onClick 람다에서 ViewModel의 decrement() 함수 호출
        Button(onClick = { onDecrement() }) {
            Text(text = "Decrement")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun CounterScreenPreview() {
    val viewModel: CounterViewModel = viewModel()
    val state by viewModel.uiState.collectAsState()

    CounterScreen(
        state = state,
        onIncrement = { viewModel.increment() },
        onDecrement = { viewModel.decrement() }
    )
}
