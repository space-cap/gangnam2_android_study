# UI State 홀더 패턴

## UI 상태의 종류

- UI 상태는 두 가지로 나눌 수 있다.
    - 화면 상태(Screen UI State)
        - 화면에 보여주기 위한 데이터
        - 예: 글 목록, 검색어, 로딩 중인지 아닌지 등
        - ViewModel 관리
    - 위젯 자체 상태(UI Element State)
        - 버튼이 눌렸는지, 체크박스가 켜졌는지 같은 상태
        - remember() 로 관리.

## 상태는 고정된 값이 아님(계속 바뀌는 이유는?)

- 상태는 고정된 값이 아님
    - 상태는 시간이나 사용자 행동에 따라 계속 바뀐다.
    - 예: 검색창에 글자를 입력하면 검색 결과가 달라진다.

- 이러한 변화는 ViewModel이 관리함.
- ViewModel 이 상태를 바꾸면, UI도 자동으로 다시 그려짐

## Android 생명주기 대응 StateFlow to State 하면서 안전 버전 사용해 보자

```kotlin
implementation("androidx.lifecycle:lifecycle-runtime-compose:2.10.0")
```

```kotlin
@Composable
fun TodoScreen(
    modifier: Modifier = Modifier,
    viewModel: TodoViewModel = viewModel(factory = TodoViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
}
```

- 라이프사이클 인식
- Started 상태에서만 수집
- 백그라운드에서 수집 중지
- 안드오링드에서는 이걸 사용하는 것을 추천







