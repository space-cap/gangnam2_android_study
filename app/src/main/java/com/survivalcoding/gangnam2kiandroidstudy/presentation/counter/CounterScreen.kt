package com.survivalcoding.gangnam2kiandroidstudy.presentation.counter

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun CounterScreen(
    modifier: Modifier = Modifier,
    // viewModel()로 기본값 제공 - Activity/Fragment가 살아있는 동안 동일한 인스턴스 유지
    // 테스트나 프리뷰에서는 파라미터로 주입 가능
    viewModel: CounterViewModel = viewModel(),
) {
    // StateFlow를 Compose State로 변환하여 값이 변경되면 자동으로 리컴포지션 발생
    // collectAsState()는 Lifecycle을 인식하여 화면이 보이지 않을 때 수집 중단
    val countValue = viewModel.count.collectAsState()

    Column(modifier = modifier) {
        // 현재 카운트 값을 텍스트로 표시
        // countValue.value로 State의 실제 값에 접근
        Text(text = "Count: ${countValue.value}")

        // 증가 버튼
        // onClick 람다에서 ViewModel의 increment() 함수 호출
        Button(onClick = { viewModel.increment() }) {
            Text(text = "Increment")
        }

        // 감소 버튼
        // onClick 람다에서 ViewModel의 decrement() 함수 호출
        Button(onClick = { viewModel.decrement() }) {
            Text(text = "Decrement")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun CounterScreenPreview() {
    CounterScreen()
}
