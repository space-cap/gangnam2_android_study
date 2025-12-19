# Event Driven 패턴

(1회성 이벤트 처리)

## UI 상태 처리용 Flow는 StateFlow

- StateFlow
  - 상태의 흐름, 항상 최신 값을 유지
  - 초기값 필수, 항상 값이 있음
  - 구독자가 없어도 최신 값 유지
  - 값이 변경되면 구독자에 알림

## 1회성 이벤트 처리 흐름

- SharedFlow 또는 Channel 사용
  - Navigation, Snackbar

- 이벤트 sealed interface 사용
  - 타입 안전성 보장
  - when 식에서 모든 케이스 처리 강제

- UI 에 표현은 LaunchedEffect 활용
  - 컴포지션이 활성화될 때 코루틴 시작
  - 컴포지션이 종료될 때 코루틴 취소

