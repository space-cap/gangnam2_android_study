package com.survivalcoding.gangnam2kiandroidstudy.presentation.test

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

/**
 * 리스트를 최상단으로 스크롤하는 버튼입니다.
 *
 * @param listState 스크롤을 제어할 LazyListState 객체
 * @param modifier 이 컴포저블에 적용할 Modifier
 */
@Composable
fun ScrollToTopButton(
    listState: LazyListState,
    modifier: Modifier = Modifier
) {
    // 컴포저블 내에서 코루틴을 실행하기 위한 스코프를 가져옵니다.
    // 이 스코프는 컴포지션이 활성 상태일 때만 코루틴을 실행하고, 컴포지션이 해제되면 자동으로 취소합니다.
    val coroutineScope = rememberCoroutineScope()

    Button(
        onClick = {
            // 버튼 클릭 시 코루틴을 실행합니다.
            coroutineScope.launch {
                // listState의 animateScrollToItem 함수를 호출하여
                // 리스트의 첫 번째 아이템(인덱스 0)으로 부드럽게 스크롤합니다.
                listState.animateScrollToItem(0)
            }
        },
        modifier = modifier
    ) {
        Text("Scroll to Top")
    }
}
