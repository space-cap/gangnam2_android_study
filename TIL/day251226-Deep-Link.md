# Deep Link

```xml

<data android:scheme="app" android:host="recipe.co" /><data android:host="recipe.co" />

```

네, 두 <data> 태그는 앱 링크(App Links) 및 딥 링크(Deep Links)를 처리하는 방식에서 중요한 차이가 있습니다.
현재 AndroidManifest.xml 파일에 있는 <intent-filter>의 컨텍스트에서 각 줄의 의미를 설명해 드리겠습니다.

```xml

<intent-filter android:autoVerify="true">
    <action android:name="android.intent.action.VIEW" />
    <category android:name="android.intent.category.DEFAULT" />
    <category android:name="android.intent.category.BROWSABLE" />

    <data android:scheme="http" />
    <data android:scheme="https" />
    <data android:scheme="app" android:host="recipe.co" />
    <data android:pathPrefix="/recipes" />
</intent-filter>

```

```xml

<data android:scheme="app" android:host="recipe.co" />
```

이 줄은 **커스텀 스킴(Custom Scheme)**을 사용한 딥 링크를 정의합니다.

• android:scheme="app": app:// 이라는 프로토콜을 사용합니다.
• android:host="recipe.co": recipe.co 라는 호스트 이름을 사용합니다.
• 동작 방식: 이 설정은 app://recipe.co/recipes/some_id와 같은 형태의 URI를 앱에서 직접 열도록 합니다. 주로 다른 앱에서 여러분의 앱을 명시적으로
호출하거나, 모바일 웹사이트에서 사용자를 앱으로 바로 리디렉션할 때 사용됩니다.
• 단점: 커스텀 스킴은 다른 앱과 충돌할 수 있으며, 만약 사용자가 앱을 설치하지 않았다면 링크가 동작하지 않는 문제가 있습니다.

만약 <data android:host="recipe.co" /> 라고만 쓴다면?
이 태그는 스킴(scheme)이 지정되지 않았기 때문에, 이전에 선언된 다른 스킴들에 host 조건을 추가하는 역할을 합니다.
위의 예제 코드에 <data android:host="recipe.co" />를 추가하면 시스템은 다음과 같이 해석합니다.

1. http://recipe.co
2. https://recipe.co

즉, android:scheme을 생략하면 바로 위에 선언된 scheme들을 따라가게 됩니다. 이 경우 http와 https 스킴을 사용하는 recipe.co 호스트로 들어오는
모든 경로(path)의 웹 URL을 앱에서 열려고 시도합니다. 이는 안드로이드 앱 링크(Android App Links)의 동작 방식입니다.
핵심 차이점 요약

| 구분     | <data android:scheme="app" android:host="recipe.co" /> | <data android:host="recipe.co" /> (단독 사용 시)   | 
|:-------|:-------------------------------------------------------|:----------------------------------------------| 
| 목적     | 커스텀 스킴 딥 링크                                            | HTTP/HTTPS 기반의 앱 링크(App Links)                | 
| 링크 예시  | app://recipe.co/...                                    | http://recipe.co/...<br>https://recipe.co/... | 
| 동작     | app:// 프로토콜을 직접 처리합니다.                                 | 표준 웹 URL(http, https)을 앱이 가로채서 엽니다.           | 
| 사용자 경험 | 앱이 설치되지 않으면 링크가 깨질 수 있습니다.                             | 앱이 없으면 웹사이트로 자연스럽게 이동합니다.                     |

결론적으로, 질문하신 두 태그는 각각 커스텀 URI 스킴을 처리할지, 아니면 표준 웹 URL을 처리할지를 결정하는 근본적인 차이가 있습니다. 현재 파일에 작성된 코드는 app:
//recipe.co 형태의 커스텀 딥 링크를 처리하기 위한 것입니다.