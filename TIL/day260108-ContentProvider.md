
# Content Provider (콘텐츠 프로바이더)

`ContentProvider`는 애플리케이션의 데이터를 다른 애플리케이션과 공유하기 위한 안드로이드의 핵심 컴포넌트입니다. 앱의 데이터 저장소에 대한 접근을 관리하며, 중앙 집중식 데이터베이스처럼 동작합니다. 이를 통해 앱 간의 데이터 공유를 안전하고 일관된 방식으로 처리할 수 있습니다.

## 1. Content Provider 기본

### 초보자를 위한 핵심 개념

다른 앱의 데이터를 사용하고 싶을 때 알아야 할 기본 사항입니다.

#### 가. ContentResolver

`ContentResolver`는 `ContentProvider`와 통신하기 위한 클라이언트 객체입니다. `Context`에서 `contentResolver`를 통해 가져올 수 있으며, Provider에 데이터를 요청(CRUD)하는 역할을 합니다.

```kotlin
// Activity나 Fragment 등 Context를 통해 ContentResolver 인스턴스를 얻습니다.
val resolver = applicationContext.contentResolver
```

#### 나. Content URI (콘텐츠 URI)

`Content URI`는 `ContentProvider` 내의 특정 데이터를 식별하는 고유 주소(URI)입니다. SQL 문의 테이블 이름과 비슷하다고 생각할 수 있습니다.

- **구조**: `scheme://authority/path/id`
  - `scheme`: 항상 `content://` 입니다.
  - `authority`: 시스템 전체에서 `ContentProvider`를 식별하는 고유한 이름입니다. (예: `com.android.contacts`)
  - `path`: 어떤 종류의 데이터(테이블)에 접근할지 지정합니다. (예: `people`)
  - `id`: (선택 사항) 테이블 내의 특정 레코드(행)를 가리킵니다. (예: `5`)

- **예시**: `content://com.android.contacts/people/5`는 연락처 앱의 'people' 데이터 중 ID가 5인 레코드를 가리킵니다.

#### 다. 데이터 조회 (`query`)

`ContentResolver.query()` 메소드를 사용하여 데이터를 조회합니다. 이는 SQL의 `SELECT` 문과 매우 유사합니다.

```kotlin
// 1. 조회할 URI
val uri: Uri = UserDictionary.Words.CONTENT_URI // 예: 사용자 사전의 단어 테이블 URI

// 2. 어떤 열(column)을 가져올지 정의합니다. (SELECT절)
val projection: Array<String> = arrayOf(
    UserDictionary.Words._ID,
    UserDictionary.Words.WORD,
    UserDictionary.Words.LOCALE
)

// 3. 어떤 행(row)을 가져올지 필터링합니다. (WHERE절)
// ? 플레이스홀더를 사용하여 SQL Injection 공격을 방지합니다.
val selectionClause: String = "${UserDictionary.Words.LOCALE} = ?"

// 4. selectionClause의 ?에 들어갈 실제 값입니다.
val selectionArgs: Array<String> = arrayOf("en_US")

// 5. 정렬 순서를 지정합니다. (ORDER BY절)
val sortOrder: String = "${UserDictionary.Words.WORD} ASC"

// 6. 쿼리 실행! 결과는 Cursor 객체로 반환됩니다.
val cursor: Cursor? = contentResolver.query(
    uri,
    projection,
    selectionClause,
    selectionArgs,
    sortOrder
)

// 7. Cursor 처리
cursor?.use {
    // 결과가 있는지 확인
    if (it.count == 0) {
        println("결과가 없습니다.")
        return@use
    }

    // 각 행을 순회하며 데이터 읽기
    while (it.moveToNext()) {
        val id = it.getLong(it.getColumnIndexOrThrow(UserDictionary.Words._ID))
        val word = it.getString(it.getColumnIndexOrThrow(UserDictionary.Words.WORD))
        val locale = it.getString(it.getColumnIndexOrThrow(UserDictionary.Words.LOCALE))
        println("ID: $id, Word: $word, Locale: $locale")
    }
} // use {} 블록이 끝나면 cursor.close()가 자동으로 호출됩니다.
```

#### 라. 데이터 추가, 수정, 삭제

- **추가 (Insert)**: `ContentValues`에 추가할 데이터를 담아 `contentResolver.insert()`를 호출합니다.

  ```kotlin
  val values = ContentValues().apply {
      put(UserDictionary.Words.WORD, "Android")
      put(UserDictionary.Words.LOCALE, "en_US")
      put(UserDictionary.Words.APP_ID, "com.example.myapp")
      put(UserDictionary.Words.FREQUENCY, 100)
  }
  // 새로 추가된 데이터의 URI를 반환합니다.
  val newUri: Uri? = contentResolver.insert(UserDictionary.Words.CONTENT_URI, values)
  ```

- **수정 (Update)**: `ContentValues`에 수정할 데이터를 담고, `selection`과 `selectionArgs`로 대상을 지정하여 `contentResolver.update()`를 호출합니다.

  ```kotlin
  val updateValues = ContentValues().apply {
      put(UserDictionary.Words.FREQUENCY, 200)
  }
  val selection = "${UserDictionary.Words.WORD} = ?"
  val selectionArgs = arrayOf("Android")
  // 수정된 행의 개수를 반환합니다.
  val updatedRows: Int = contentResolver.update(UserDictionary.Words.CONTENT_URI, updateValues, selection, selectionArgs)
  ```

- **삭제 (Delete)**: `selection`과 `selectionArgs`로 대상을 지정하여 `contentResolver.delete()`를 호출합니다.

  ```kotlin
  val selection = "${UserDictionary.Words.WORD} = ?"
  val selectionArgs = arrayOf("Android")
  // 삭제된 행의 개수를 반환합니다.
  val deletedRows: Int = contentResolver.delete(UserDictionary.Words.CONTENT_URI, selection, selectionArgs)
  ```

## 2. Content Provider 생성하기

### 중급자를 위한 가이드

내 앱의 데이터를 다른 앱과 공유해야 할 때 `ContentProvider`를 직접 만들 수 있습니다.

#### 가. 구현 단계

1.  **데이터 저장소 설계**: SQLite 데이터베이스 (Room 사용 권장), 파일 등 데이터를 저장할 방법을 결정합니다.
2.  **`ContentProvider` 클래스 구현**: `ContentProvider` 추상 클래스를 상속받아 6개의 필수 메소드를 구현합니다.
3.  **URI 설계**: Provider의 `authority`와 `path`를 정의하여 Content URI 구조를 설계합니다.
4.  **매니페스트에 선언**: `<provider>` 태그를 사용하여 `AndroidManifest.xml`에 `ContentProvider`를 등록합니다.

#### 나. 필수 메소드 구현

`ContentProvider`를 상속하면 다음 메소드들을 반드시 구현해야 합니다. 이 메소드들은 클라이언트의 `ContentResolver` 요청에 대응됩니다.

- `onCreate()`: Provider 초기화를 위해 호출됩니다. (UI 스레드에서 실행되므로 오래 걸리는 작업은 피해야 합니다.)
- `query()`: 데이터 조회를 처리합니다. `Cursor`를 반환해야 합니다.
- `insert()`: 데이터 삽입을 처리합니다. 새로 생성된 데이터의 URI를 반환해야 합니다.
- `update()`: 데이터 수정을 처리합니다. 수정된 행의 개수를 반환해야 합니다.
- `delete()`: 데이터 삭제를 처리합니다. 삭제된 행의 개수를 반환해야 합니다.
- `getType()`: 주어진 URI에 해당하는 데이터의 MIME 타입을 반환해야 합니다.

#### 다. URI 매칭 (`UriMatcher`)

`UriMatcher`는 들어오는 URI 패턴을 정수 값과 매핑하여 어떤 작업을 수행할지 쉽게 구분하도록 도와줍니다.

```kotlin
class MyContentProvider : ContentProvider() {

    private lateinit var dbHelper: MyDbHelper

    companion object {
        const val AUTHORITY = "com.example.myapp.provider"
        val TABLE_URI: Uri = Uri.parse("content://$AUTHORITY/items")

        // UriMatcher 설정
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            // content://com.example.myapp.provider/items
            addURI(AUTHORITY, "items", 1) // 여러 행
            // content://com.example.myapp.provider/items/#
            addURI(AUTHORITY, "items/#", 2) // 단일 행 (숫자 ID)
        }
    }

    override fun onCreate(): Boolean {
        // 데이터베이스 헬퍼 초기화
        dbHelper = MyDbHelper(context!!)
        return true
    }

    override fun query(uri: Uri, ...): Cursor? {
        val db = dbHelper.readableDatabase
        val match = uriMatcher.match(uri)
        val cursor: Cursor

        when (match) {
            1 -> { // 모든 아이템 조회
                cursor = db.query("items", projection, selection, selectionArgs, null, null, sortOrder)
            }
            2 -> { // 특정 아이템 조회
                val id = uri.lastPathSegment // URI에서 ID 추출
                val selectionWithId = "_ID = ?"
                val selectionArgsWithId = arrayOf(id)
                cursor = db.query("items", projection, selectionWithId, selectionArgsWithId, null, null, sortOrder)
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
        return cursor
    }
    // insert(), update(), delete(), getType() 구현...
}
```

#### 라. 매니페스트 선언

`AndroidManifest.xml`에 `<provider>`를 등록해야 시스템이 Provider를 인식하고 다른 앱이 접근할 수 있습니다.

```xml
<manifest ...>
    <application ...>
        <provider
            android:name=".provider.MyContentProvider"
            android:authorities="com.example.myapp.provider"
            android:exported="true"
            android:readPermission="com.example.myapp.permission.READ_DATA"
            android:writePermission="com.example.myapp.permission.WRITE_DATA" />
    </application>
</manifest>
```

- `android:name`: 구현한 `ContentProvider` 클래스
- `android:authorities`: 시스템 전체에서 고유한 권한(authority) 이름. 보통 패키지 이름 + ".provider"로 짓습니다.
- `android:exported="true"`: 다른 앱에서 이 Provider를 사용할 수 있도록 허용합니다.
- `android:readPermission`/`writePermission`: Provider에 접근하기 위해 필요한 권한을 지정하여 데이터를 보호합니다.

## 3. 고급 주제

### 고급 사용자를 위한 심화 내용

#### 가. 계약 클래스 (Contract Class)

`Contract Class`는 Provider의 URI, 테이블 및 열 이름, MIME 타입 등 메타데이터를 상수로 정의하는 클래스입니다. 이는 Provider의 "공식 API" 역할을 하여 다른 개발자가 Provider를 쉽게 사용하고, 내부 구현이 변경되더라도 영향을 받지 않도록 합니다.

```kotlin
// Contract 클래스 예시
object MyProviderContract {
    const val AUTHORITY = "com.example.myapp.provider"
    val BASE_CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY")

    object Items {
        const val PATH = "items"
        val CONTENT_URI: Uri = BASE_CONTENT_URI.buildUpon().appendPath(PATH).build()

        // MIME 타입 정의
        const val CONTENT_TYPE = "vnd.android.cursor.dir/vnd.$AUTHORITY.$PATH" // 여러 행
        const val CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.$AUTHORITY.$PATH" // 단일 행

        // 테이블 열 이름 정의
        const val _ID = "_id"
        const val COLUMN_NAME = "name"
        const val COLUMN_PRICE = "price"
    }
}
```

#### 나. 권한 관리 (Permissions)

`ContentProvider`의 데이터를 보호하는 것은 매우 중요합니다. 매니페스트에서 권한을 설정하여 데이터 접근을 제어할 수 있습니다.

- **`<permission>`**: `<uses-permission>`과 달리, 내 앱의 컴포넌트를 보호하기 위한 새로운 권한을 직접 정의합니다.
- **임시 권한 부여 (`grantUriPermissions`)**: 영구적인 권한 없이 특정 URI에 대한 접근을 일시적으로 허용하는 강력한 기능입니다. 예를 들어, 이메일 앱이 갤러리 앱에게 이메일 첨부 파일(사진)을 보여줄 수 있도록 일회성 읽기 권한을 부여할 수 있습니다.
  - 매니페스트 `<provider>`에 `android:grantUriPermissions="true"`를 설정합니다.
  - `Intent`에 `FLAG_GRANT_READ_URI_PERMISSION` 또는 `FLAG_GRANT_WRITE_URI_PERMISSION` 플래그를 추가하여 권한을 요청하는 앱에게 전달합니다.

#### 다. 파일 공유와 `FileProvider`

`ContentProvider`는 데이터베이스뿐만 아니라 파일 공유에도 사용됩니다. 특히 `FileProvider`는 `ContentProvider`의 특별한 하위 클래스로, 앱의 비공개 디렉터리에 있는 파일을 안전하게 공유할 수 있도록 설계되었습니다. `file://` URI 대신 `content://` URI를 생성하여 다른 앱에 안전하게 파일 접근 권한을 부여합니다.

#### 라. 스레드 안전성 (Thread Safety)

`onCreate()`를 제외한 모든 `ContentProvider` 메소드는 여러 스레드에서 동시에 호출될 수 있습니다. 따라서 데이터베이스 접근과 같은 코드 블록을 동기화(synchronized) 처리하는 등 스레드에 안전하게 구현해야 합니다.

