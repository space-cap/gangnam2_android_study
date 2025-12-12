# Navigation

## Android 에서 화면 이동 방식의 역사

-   **Activity 간 화면 전환**:
    -   `Intent`를 사용하여 다른 Activity를 시작합니다.
    -   단점: Activity 간 데이터 전달이 번거롭고, 화면 전환 애니메이션 커스터마이징이 제한적입니다. Task와 Back Stack 관리가 복잡해질 수 있습니다.
-   **Fragment 간 화면 전환 (Single Activity Architecture)**:
    -   하나의 Activity 내에서 `FragmentManager`를 통해 Fragment를 교체합니다.
    -   장점: 화면 간 상태 공유가 용이하고, 더 유연한 UI 구성이 가능합니다.
    -   단점: `FragmentTransaction` 관리가 번거롭고, 복잡한 nested Fragment 구조에서 생명주기 문제가 발생할 수 있습니다.
-   **Jetpack Navigation (XML)**:
    -   Navigation Graph(XML 리소스)를 사용하여 화면 흐름을 시각적으로 관리합니다.
    -   `NavController`가 Fragment 트랜잭션 및 Up/Back 동작을 자동으로 처리합니다.
    -   Safe Args 플러그인을 통해 타입-세이프(type-safe)한 방식으로 화면 간 데이터를 전달할 수 있습니다.
-   **Jetpack Navigation (Compose)**:
    -   Composable 함수를 화면의 Destination으로 사용합니다.
    -   `NavHost` Composable을 사용하여 네비게이션 그래프를 설정합니다.
    -   `NavController`를 사용하여 Composable 간의 이동을 관리합니다.
    -   Navigation 2 (Android 전용) / Navigation 3 (KMP 지원) 으로 버전이 나뉩니다.

## ViewModel의 생명주기

-   **Activity/Fragment와 ViewModel**: ViewModel은 `ViewModelStoreOwner`(예: `ComponentActivity`, `Fragment`)의 생명주기에 종속됩니다. 화면 회전과 같은 설정 변경에도 상태를 유지하지만, Owner가 완전히 종료되면 ViewModel도 파괴됩니다.
-   **Navigation과 ViewModel**: Jetpack Navigation 사용 시 ViewModel의 범위를 Navigation Graph로 지정할 수 있습니다. 이렇게 하면 해당 그래프에 속한 여러 Destination(Fragment/Composable)간에 UI 상태를 공유하는 것이 매우 쉬워집니다. 예를 들어, 로그인 흐름이나 구매 흐름 전체에서 동일한 ViewModel 인스턴스를 공유할 수 있습니다.

## Jetpack Navigation 구성 요소

-   **NavController**: 네비게이션 동작을 수행하는 핵심 컨트롤러입니다. `navigate()`, `popBackStack()` 등의 메서드를 제공합니다.
-   **NavHost**: 네비게이션 그래프의 Destination들을 표시하는 컨테이너 역할을 합니다.
-   **Navigation Graph**: 모든 Destination과 Action을 정의한 XML 파일 또는 Composable 함수의 집합입니다. 화면 흐름의 청사진 역할을 합니다.

## Navigation Compose (Nav3) 주요 특징

Navigation 3는 Kotlin Multiplatform(KMP)을 지원하도록 재설계되었습니다.

-   **NavKey**: 화면(Destination)을 나타내는 타입-세이프 직렬화 가능한 클래스 또는 객체입니다. 화면에 전달할 인수를 직접 포함할 수 있습니다.

    ```kotlin
    @Serializable
    data class Profile(val userId: String) : NavKey
    ```

-   **BackStack**: 화면 스택을 관리합니다. `NavHost`는 `BackStack`의 변경을 관찰하여 현재 화면을 표시합니다.
-   **NavDisplay**: `BackStack`의 최상위 `NavKey`를 UI로 표시하는 역할을 합니다. Android에서는 주로 `NavHost`가 이 역할을 수행합니다.
-   **장점**:
    -   **타입 안정성**: 화면과 인수를 클래스로 정의하므로 컴파일 타임에 오류를 잡을 수 있습니다.
    -   **KMP 지원**: Android 뿐만 아니라 다른 플랫폼에서도 동일한 네비게이션 로직을 공유할 수 있습니다.
    -   **ViewModel 통합**: `ViewModel`을 `NavKey`와 함께 사용하여 특정 화면이나 네비게이션 흐름에 대한 상태를 쉽게 관리할 수 있습니다.

## 추가적으로 고려할 점

-   **Deep Linking**: 앱 외부(예: 푸시 알림, 웹 링크)에서 특정 화면으로 직접 이동하게 해주는 기능입니다. Navigation 컴포넌트는 `PendingIntent`를 생성하여 딥링크를 쉽게 구현할 수 있도록 지원합니다.
-   **화면 전환 애니메이션**: `NavOptions`나 Compose의 `AnimatedNavHost`를 사용하여 Destination 간 전환 애니메이션을 커스터마이징할 수 있습니다.
-   **중첩된 네비게이션 그래프(Nested Navigation Graphs)**: 관련된 화면 그룹을 별도의 네비게이션 그래프로 묶어 재사용하고 관리하기 용이하게 만들 수 있습니다.

## 공식문서

-   Nav3 : https://developer.android.com/guide/navigation/navigation-3?hl=ko
-   Compose Navigation: https://developer.android.com/jetpack/compose/navigation?hl=ko
