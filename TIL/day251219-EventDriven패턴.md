# Event Driven 패턴

(1회성 이벤트 처리)

## UI 상태 처리용 Flow는 StateFlow

- StateFlow
  - 상태의 흐름, 항상 최신 값을 유지
  - 초기값 필수, 항상 값이 있음
  - 구독자가 없어도 최신 값 유지
  - 값이 변경되면 구독자에 알림

## 1회성 이벤트 처리 흐름

### 1. 1회성 이벤트 처리를 위한 `SharedFlow` 또는 `Channel`

`StateFlow`는 상태를 나타내므로 항상 최신 값을 가지고 있고, 화면 회전과 같이 UI가 재생성될 때 마지막 값을 다시 방출합니다. 그래서 스낵바 표시나 화면 이동 같은 1회성 이벤트에 사용하면 의도치 않게 이벤트가 반복해서 발생하는 문제가 생길 수 있습니다.

이러한 1회성 이벤트를 처리하기 위해 `SharedFlow`나 `Channel`을 사용하는 것이 좋습니다.

#### `SharedFlow` 사용하기

`SharedFlow`는 새로운 구독자에게 이전 값을 재전송할지(`replay` 캐시) 여부를 설정할 수 있어 1회성 이벤트를 처리하는 데 매우 유용합니다.

-   **`replay = 0` (기본값)**: 새로운 구독자는 구독 시작 이후에 발생하는 이벤트만 받게 됩니다. 이전에 발생한 이벤트는 받지 않으므로 1회성 이벤트 처리에 적합합니다.
-   **`extraBufferCapacity`**: `replay`와 별도로 버퍼를 두어 이벤트를 저장할 수 있습니다. 이벤트가 빠르게 발생할 때 구독자가 놓치지 않도록 도와줍니다.
-   **`onBufferOverflow`**: 버퍼가 가득 찼을 때 어떻게 처리할지 정책을 정합니다. `BufferOverflow.DROP_OLDEST`는 가장 오래된 이벤트를 버려서 새로운 이벤트를 위한 공간을 만듭니다.

**ViewModel 예제 (`SharedFlow`)**

`replay` 캐시를 사용하지 않도록 설정하여, 새로운 구독자가 이전 이벤트를 받지 못하게 합니다.

```kotlin
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class EventViewModel : ViewModel() {

    // replay=0 으로 설정하여 새로운 구독자가 이전 이벤트를 받지 않도록 합니다.
    private val _eventFlow = MutableSharedFlow<UiEvent>(replay = 0)
    val eventFlow = _eventFlow.asSharedFlow()

    fun showSnackbar(message: String) {
        viewModelScope.launch {
            _eventFlow.emit(UiEvent.ShowSnackbar(message))
        }
    }
}

sealed interface UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent
}
```

#### `Channel` 사용하기

`Channel`은 코루틴 간에 데이터를 안전하게 주고받기 위해 사용되는 통로입니다. `Channel`로 보낸 데이터는 **오직 하나의 구독자**만이 받을 수 있습니다.

`Channel`은 "fire-and-forget" 스타일의 1회성 이벤트를 보내기에 적합합니다. `receiveAsFlow()` 확장 함수를 사용하면 `Channel`을 Cold Flow로 변환하여 UI에서 쉽게 사용할 수 있습니다.

**ViewModel 예제 (`Channel`)**

```kotlin
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class EventViewModel : ViewModel() {

    private val _eventChannel = Channel<UiEvent>()
    // Channel을 Flow로 변환하여 UI에서 쉽게 구독할 수 있도록 합니다.
    val eventFlow = _eventChannel.receiveAsFlow()

    fun showDialog(title: String, message: String) {
        viewModelScope.launch {
            _eventChannel.send(UiEvent.ShowDialog(title, message))
        }
    }
}

sealed interface UiEvent {
    data class ShowDialog(val title: String, val message: String) : UiEvent
}
```

#### `SharedFlow` vs `Channel`

-   **`SharedFlow`**: 하나의 이벤트를 여러 구독자가 동시에 처리해야 할 때 유용합니다. 예를 들어, 특정 이벤트가 발생했을 때 분석 로그도 남기고 UI도 업데이트해야 하는 경우에 적합합니다. UI 이벤트 처리에는 일반적으로 `SharedFlow`가 더 유연하고 권장됩니다.
-   **`Channel`**: 이벤트가 반드시 **한 번만, 단일 구독자에 의해** 처리되어야 할 때 사용합니다. 내비게이션 이벤트처럼 두 번 실행되면 곤란한 경우에 특히 유용합니다.

결론적으로, 안드로이드 앱의 MVI 아키텍처에서 ViewModel과 UI 사이의 1회성 이벤트를 전달하는 데에는 둘 다 좋은 선택지이지만, 일반적으로는 더 유연한 `SharedFlow`가 선호되는 경향이 있습니다.

### 2. 이벤트 타입을 위한 `Sealed Interface`

다양한 종류의 1회성 이벤트를 관리하기 위해 `sealed interface` (봉인된 인터페이스)를 사용하면 좋습니다. 이렇게 하면 이벤트 유형을 명확하게 정의할 수 있고, `when` 표현식을 사용할 때 모든 이벤트 케이스를 처리하도록 강제할 수 있어 코드의 안정성을 높일 수 있습니다.


```kotlin
sealed interface MyEvent {
    data class ShowSnackbar(val message: String) : MyEvent
    object NavigateToDetail : MyEvent
}
```

### 3. UI에서 이벤트를 처리하는 `LaunchedEffect`

ViewModel에서 발생한 이벤트를 UI(Composable 함수)에서 처리할 때는 `LaunchedEffect`를 사용합니다. `LaunchedEffect`는 특정 키 값이 변경될 때만 코루틴을 실행하므로, 불필요한 재실행을 막고 정확한 시점에 이벤트를 처리할 수 있게 해줍니다.

### 종합 예제 코드

아래는 위 세 가지 개념을 모두 적용한 간단한 예제입니다.

**ViewModel:**


```kotlin
// TodoViewModel.kt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

// 1. Sealed Interface로 이벤트 정의
sealed interface TodoEvent {
    data class ShowSnackbar(val message: String) : TodoEvent
    object NavigateToNextScreen : TodoEvent
}

class TodoViewModel : ViewModel() {

    // 2. 1회성 이벤트를 위한 SharedFlow 사용
    private val _eventFlow = MutableSharedFlow<TodoEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onSaveButtonClick() {
        viewModelScope.launch {
            // 비동기 작업 (예: 데이터 저장) 후 이벤트 발생
            _eventFlow.emit(TodoEvent.ShowSnackbar("저장되었습니다!"))
        }
    }

    fun onGoToNextScreenClick() {
        viewModelScope.launch {
            _eventFlow.emit(TodoEvent.NavigateToNextScreen)
        }
    }
}
```



**UI (Composable):**


```kotlin
// TodoScreen.kt

import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TodoScreen(
    viewModel: TodoViewModel = viewModel(),
    onNavigateToNextScreen: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    // 3. LaunchedEffect를 사용해 ViewModel의 이벤트를 구독하고 처리
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is TodoEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message)
                }
                is TodoEvent.NavigateToNextScreen -> {
                    onNavigateToNextScreen()
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        // ... UI 컨텐츠
        Button(onClick = { viewModel.onSaveButtonClick() }) {
            Text("저장")
        }
        Button(onClick = { viewModel.onGoToNextScreenClick() }) {
            Text("다음 화면으로")
        }
    }
}
```

이 예제처럼 `ViewModel`에서 발생한 이벤트를 `SharedFlow`를 통해 전달하고, Composable에서는 `LaunchedEffect`로 이벤트를 받아 스낵바를 보여주거나 화면을 이동하는 등의 UI 관련 작업을 수행하면 됩니다.

궁금한 점이 있다면 언제든지 다시 질문해주세요
