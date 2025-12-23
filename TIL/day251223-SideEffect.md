# Compose - SideEffect

## Side Effects 기초

- 컴포저블은 side-effect(부작용)이 없어야 한다. 하지만 앱의 상태를 변경해야 하는 경우, 컴포저블의 수명 주기를 인지하는 제어된 환경에서 변경하는 행위를 호출해야
  한다.
- Side-effects는 컴포저블 스코프 외부에서 발생하는 앱 상태의 변경이다.

## State 및 Effect의 사용 사례

- State 관리 문서에 설명된 대로 앱의 상태를 변경해야 하는 경우 이런 부작용이 예측 가능한 방식으로 실행될 수 있도록 8가지 Effect API를 사용해야 한다.
- Effect는 UI를 리턴하지 않고 composition이 완료될 때 부작용이 실행되도록 하는 Composable 함수이다.
- 반응형 UI는 본질적으로 비동기식이며, Jetpack Compose는 API 수준에서 코루틴을 사용하여 이 문제를 해결한다.




