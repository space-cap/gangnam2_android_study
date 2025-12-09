# MVVM 패턴

MVVM(Model-View-ViewModel)은 안드로이드 앱 개발에서 비즈니스 로직과 UI를 명확하게 분리하는 아키텍처 패턴입니다. 이 패턴은 Microsoft의 WPF 프레임워크에서 유래했으며, MVC 패턴을 기반으로 현대 UI 요구사항에 맞게 설계되었습니다. MVVM은 View와 Model 사이의 의존성뿐만 아니라 View와 Controller 사이의 의존성도 고려하여 코드의 재사용성, 테스트 용이성, 유지보수성을 크게 향상시킵니다.

## MVVM 구성 요소

### Model
모델은 데이터 관리와 비즈니스 로직을 담당합니다. 내부 DB나 서버에 데이터 CRUD(Create, Read, Update, Delete)를 수행하며, 네트워크 통신이나 로컬 데이터베이스 처리 후 ViewModel에 결과를 반환합니다.

### View
View는 Activity와 Fragment를 포함한 사용자 인터페이스 영역입니다. 사용자에게 화면을 표시하고 입력을 받으며, ViewModel의 데이터를 관찰(Observing)하여 자동으로 UI를 갱신합니다. View는 ViewModel에 필요한 데이터를 요청하고, 데이터 바인딩을 통해 화면에 자동으로 반영되기 때문에 직접 UI를 변경할 필요가 없습니다.

### ViewModel
ViewModel은 View와 Model 사이의 중계 역할을 수행합니다. View에서 발생하는 이벤트를 감지하고 비즈니스 로직을 처리하며, Model로부터 데이터를 가져와 가공한 후 LiveData나 StateFlow 등을 통해 View에 전달합니다. ViewModel은 View를 직접 참조하지 않기 때문에 의존성이 단방향(View → ViewModel → Model)으로만 흐릅니다.





