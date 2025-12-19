# Event Driven 패턴

(1회성 이벤트 처리)

## UI 상태 처리용 Flow는 StateFlow

*   **StateFlow**
    *   상태의 흐름, 항상 최신 값을 유지
    *   초기값 필수, 항상 값이 있음
    *   구독자가 없어도 최신 값 유지
    *   값이 변경되면 구독자에 알림

## 1회성 이벤트 처리 흐름

*   **SharedFlow 또는 Channel 사용**
    *   Navigation, Snackbar 등 1회성 이벤트에 사용
*   **이벤트 타입을 Sealed Interface로 정의**
    *   타입 안전성 보장
    *   `when` 식에서 모든 케이스 처리 강제
*   **UI에서 LaunchedEffect로 이벤트 처리**
    *   컴포지션이 활성화될 때 코루틴 시작
    *   컴포지션이 종료될 때 코루틴 취소

---

## 상세 설명 및 단계별 예제

### 문제 상황: `StateFlow`와 1회성 이벤트

`StateFlow`는 UI의 '상태'를 표현하는 데 아주 효과적입니다. 하지만 스낵바 표시, 화면 이동, 다이얼로그 열기 같은 **1회성 이벤트**에 `StateFlow`를 사용하면 문제가 발생할 수 있습니다. `StateFlow`는 항상 최신 값을 가지고 있으며, 화면 회전 등으로 UI가 재생성될 때 마지막 값을 새로운 구독자(UI)에게 다시 전달합니다. 이 때문에 원하지 않게 스낵바가 다시 뜨거나 화면 이동이 반복될 수 있습니다.

이 문제를 해결하기 위해 1회성 이벤트는 '이벤트 기반'으로 처리하는 것이 좋습니다. 아래 단계별 가이드를 통해 알아보겠습니다.

### 1단계: `Sealed Interface`로 이벤트 정의하기

먼저, 한 화면에서 발생할 수 있는 모든 1회성 이벤트를 한 곳에서 명확하게 정의하는 것이 좋습니다. 이때 `sealed interface`(봉인된 인터페이스)를 사용하면 매우 효과적입니다.

*   **왜 `Sealed Interface`를 사용할까요?**
    *   **타입 안전성**: 어떤 이벤트들이 발생 가능한지 명확히 정의할 수 있습니다.
    *   **컴파일러 경고**: `when` 표현식으로 이벤트를 처리할 때, 모든 이벤트 타입을 처리했는지 컴파일러가 검사해주어 실수를 방지합니다.
    *   **코드 구성**: 관련된 이벤트들을 하나의 그룹으로 묶어 코드를 더 읽기 쉽고 유지보수하기 좋게 만듭니다.

#### **예제: UI 이벤트 정의하기**

스낵바를 보여주는 이벤트와 상세 화면으로 이동하는 이벤트를 정의해 보겠습니다.

```kotlin
// MyScreenEvent.kt

sealed interface MyScreenEvent {
    data class ShowSnackbar(val message: String) : MyScreenEvent
    object NavigateToDetails : MyScreenEvent
}
```

---

### 2단계: ViewModel에서 `SharedFlow`로 이벤트 생성 및 전송

ViewModel은 비즈니스 로직을 처리하고, 그 결과로 1회성 이벤트를 발생시키는 역할을 합니다. 이때 `MutableSharedFlow`를 사용하여 이벤트를 UI로 전달합니다.

*   **왜 `SharedFlow`를 사용할까요?**
    `SharedFlow`는 여러 구독자에게 값을 전달(broadcast)하는 '핫 스트림'입니다. 1회성 이벤트를 위해 `replay = 0`으로 설정하는 것이 핵심입니다.
    *   **`replay = 0`**: 이 설정은 새로운 구독자가 이전에 발생했던 값을 받지 못하게 합니다. 즉, 구독을 시작한 이후에 발생하는 새로운 이벤트만 받게 되므로, 화면 회전 시 이벤트가 재발생하는 문제를 막아줍니다.
    *   **핫 스트림(Hot Stream)**: `viewModelScope` 내에서 계속 활성 상태를 유지하며, 구독자가 없어도 이벤트를 발행할 수 있습니다.

#### **예제: `SharedFlow`를 사용하는 ViewModel**

`MutableSharedFlow`를 사용해 위에서 정의한 `MyScreenEvent`를 보내는 ViewModel 예제입니다.

```kotlin
// MyViewModel.kt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {

    // 1. private 가변 SharedFlow를 생성합니다.
    //    replay=0 으로 설정하여 새 구독자가 이전 이벤트를 받지 못하게 합니다.
    private val _eventFlow = MutableSharedFlow<MyScreenEvent>(replay = 0)

    // 2. 외부에는 변경 불가능한 public SharedFlow로 노출합니다.
    val eventFlow = _eventFlow.asSharedFlow()

    // 3. 저장 버튼 클릭 시 스낵바 이벤트를 발생시키는 함수입니다.
    fun onSaveButtonClick() {
        viewModelScope.launch {
            // ... 저장 로직 수행 ...

            // 로직 완료 후 이벤트를 발생시킵니다.
            _eventFlow.emit(MyScreenEvent.ShowSnackbar("데이터가 저장되었습니다!"))
        }
    }

    // 4. 상세 보기 버튼 클릭 시 내비게이션 이벤트를 발생시키는 함수입니다.
    fun onDetailsButtonClick() {
        viewModelScope.launch {
            _eventFlow.emit(MyScreenEvent.NavigateToDetails)
        }
    }
}
```

---

### 3단계: UI에서 `LaunchedEffect`로 이벤트 수신 및 처리

Composable UI에서는 ViewModel로부터 오는 이벤트를 수신하고, 스낵바를 보여주는 등의 실제 동작을 수행해야 합니다. 이때 가장 적합한 도구는 `LaunchedEffect`입니다.

*   **왜 `LaunchedEffect`를 사용할까요?**
    `LaunchedEffect`는 컴포지션에 진입했을 때 코루틴을 실행하는 Composable 함수입니다.
    *   **생명주기 인식**: `LaunchedEffect`가 컴포지션을 벗어나면 코루틴이 자동으로 취소되어 메모리 누수를 방지합니다.
    *   **제어된 재실행**: `key` 파라미터를 받아서, 이 `key`값이 변경될 때만 코루틴을 재시작합니다. `key1 = true`와 같이 상수를 사용하면, 리컴포지션이 계속 발생해도 이벤트 수집 코루틴은 단 한 번만 실행되고 유지됩니다.

#### **예제: `LaunchedEffect`를 사용하는 UI**

`MyViewModel`의 이벤트를 수집하고 처리하는 화면 예제입니다.

```kotlin
// MyScreen.kt

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MyScreen(
    viewModel: MyViewModel = viewModel(),
    onNavigateToDetails: () -> Unit // 내비게이션을 위한 콜백 함수
) {
    val snackbarHostState = remember { SnackbarHostState() }

    // LaunchedEffect를 사용해 ViewModel의 이벤트를 수집합니다.
    // key1 = true는 이 블록이 리컴포지션에 의해 재실행되지 않고,
    // 단 한 번만 실행되도록 보장합니다.
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is MyScreenEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(message = event.message)
                }
                is MyScreenEvent.NavigateToDetails -> {
                    onNavigateToDetails()
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        // ... 화면 컨텐츠 ...

        // 예시: 이벤트를 발생시키는 버튼들
        Button(onClick = { viewModel.onSaveButtonClick() }) {
            Text("저장")
        }
        Button(onClick = { viewModel.onDetailsButtonClick() }) {
            Text("상세보기")
        }
    }
}
```

---

## 대안: `Channel` 사용하기

`Channel` 또한 1회성 이벤트를 보내는 데 사용할 수 있습니다. `SharedFlow`와 달리 `Channel`로 보낸 값은 **단 하나의 구독자**만 받을 수 있습니다. 이벤트가 수신되면 채널에서 즉시 사라집니다.

*   **`SharedFlow` vs `Channel`**
    *   **`SharedFlow`**: 하나의 이벤트를 여러 구독자가 동시에 관찰해야 할 때 유용합니다 (예: UI 처리용 구독자, 분석 로그용 구독자). 일반적으로 UI 이벤트 처리에는 더 유연한 `SharedFlow`가 권장됩니다.
    *   **`Channel`**: 이벤트가 반드시 **단 한 번, 단 하나의 구독자**에 의해서만 처리되어야 함을 보장하고 싶을 때 사용합니다. 두 번 실행되면 곤란한 화면 이동 같은 경우에 특히 유용합니다.

#### **예제: `Channel`을 사용하는 ViewModel**

`Channel`을 사용하려면 `receiveAsFlow()` 확장 함수를 통해 `Flow`로 변환하여 외부에 노출합니다.

```kotlin
// ChannelEventViewModel.kt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ChannelEventViewModel : ViewModel() {

    private val _eventChannel = Channel<MyScreenEvent>()
    val eventFlow = _eventChannel.receiveAsFlow() // Flow로 변환하여 노출

    fun onNavigate() {
        viewModelScope.launch {
            _eventChannel.send(MyScreenEvent.NavigateToDetails)
        }
    }
}
```

`Channel`을 사용하더라도 UI에서 `LaunchedEffect`로 이벤트를 수신하는 코드는 동일합니다.
