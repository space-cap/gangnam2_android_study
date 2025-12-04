# 프로젝트 구조 리팩토링 가이드

이 문서는 현재 프로젝트 구조를 클린 아키텍처(Clean Architecture) 기반의 계층형 구조로 리팩토링하는 과정을 안내합니다.

## 1. 왜 리팩토링을 해야 할까?

-   **관심사 분리 (Separation of Concerns)**: UI, 비즈니스 로직, 데이터 소스를 분리하여 코드의 복잡성을 낮춥니다.
-   **유지보수성 향상**: 기능별로 코드가 모여있어 특정 기능을 수정하거나 추가할 때 다른 코드에 미치는 영향을 최소화할 수 있습니다.
-   **테스트 용이성**: 각 계층이 독립적으로 존재하여 단위 테스트(Unit Test) 작성이 쉬워집니다.
-   **확장성**: 새로운 기능이 추가되거나 라이브러리가 변경되어도 구조적인 변경을 최소화할 수 있습니다.

## 2. 목표 폴더 구조

리팩토링을 통해 완성될 폴더 구조는 다음과 같습니다.

```
app/src/main/java/com/survivalcoding/gangnam2kiandroidstudy/
├── data/                 # 데이터 소스 (네트워크, DB 등)
│   ├── repository/
│   │   └── UserRepositoryImpl.kt
│   ├── datasource/
│   │   ├── remote/
│   │   │   └── UserApi.kt
│   │   └── local/
│   │       └── UserDao.kt
│   └── dto/                # Data Transfer Objects
│       └── UserDto.kt
│
├── domain/               # 핵심 비즈니스 로직
│   ├── model/
│   │   └── User.kt
│   ├── repository/
│   │   └── UserRepository.kt
│   └── usecase/
│       ├── GetUserUseCase.kt
│       └── SignInUseCase.kt
│
└── presentation/         # UI 및 상태 관리
    ├── ui/
    │   ├── AppColors.kt
    │   ├── AppFonts.kt
    │   └── AppTextStyles.kt
    ├── component/
    │   ├── BigButton.kt
    │   ├── InputField.kt
    │   └── Tabs.kt
    ├── signin/
    │   ├── SignInScreen.kt
    │   ├── SignInViewModel.kt
    │   └── SignInState.kt
    ├── signup/
    │   ├── SignUpScreen.kt
    │   ├── SignUpViewModel.kt
    │   └── SignUpState.kt
    └── MainActivity.kt
```

## 3. 리팩토링 단계

### 1단계: 폴더 생성

1.  `app/src/main/java/com/survivalcoding/gangnam2kiandroidstudy/` 경로 아래에 다음 폴더들을 생성합니다.
    -   `domain`
    -   `presentation/signin`
    -   `presentation/signup`

### 2단계: 파일 이동

기존 파일들을 새로운 구조에 맞게 이동합니다.

1.  **`ui` 폴더 이동**:
    -   **대상**: `ui` 폴더 전체
    -   **이동 경로**: `presentation/` 폴더 아래로 이동합니다.
    -   **최종 경로**: `presentation/ui/`

2.  **`MainActivity.kt` 이동**:
    -   **대상**: `MainActivity.kt`
    -   **이동 경로**: `presentation/` 폴더 아래로 이동합니다.
    -   **최종 경로**: `presentation/MainActivity.kt`

파일을 이동하면 Android Studio가 자동으로 `package` 선언과 `import` 구문을 업데이트합니다. 만약 자동으로 변경되지 않으면, 각 파일의 맨 위에 있는 `package` 경로를 수동으로 수정해야 합니다.

**예시:**
-   `AppColors.kt`의 패키지 선언 변경
    -   **기존**: `package com.survivalcoding.gangnam2kiandroidstudy.ui`
    -   **변경 후**: `package com.survivalcoding.gangnam2kiandroidstudy.presentation.ui`

### 4. 앞으로의 개발 방향

-   **새로운 화면**: 새로운 화면(예: 프로필)을 추가할 때는 `presentation/profile`과 같이 기능별 패키지를 만들고 그 안에 `ProfileScreen.kt`, `ProfileViewModel.kt` 등을 작성합니다.
-   **공통 UI**: 여러 화면에서 재사용되는 UI 컴포넌트는 지금처럼 `presentation/component`에 만듭니다.
-   **비즈니스 로직**: 특정 기능에 국한되지 않는 핵심 비즈니스 로직은 `domain/usecase`에 작성합니다.
-   **데이터 처리**: 네트워크 API 호출이나 데이터베이스 접근과 같은 로직은 `data` 레이어에 작성합니다.
