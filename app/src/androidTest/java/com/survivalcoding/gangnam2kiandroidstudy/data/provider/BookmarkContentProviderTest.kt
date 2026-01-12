package com.survivalcoding.gangnam2kiandroidstudy.data.provider

import android.database.DatabaseUtils
import android.util.Log
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.survivalcoding.gangnam2kiandroidstudy.data.dao.BookmarkDao
import com.survivalcoding.gangnam2kiandroidstudy.data.database.AppDatabase
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

@RunWith(AndroidJUnit4::class)
class BookmarkContentProviderTest : KoinTest {

    // Koin을 통해 주입될 의존성들. 실제 앱 DB에 접근하게 됩니다.
    private val bookmarkDao: BookmarkDao by inject()
    private val db: AppDatabase by inject()

    @Before
    fun setUp() {
        // 실제 앱의 컨텍스트를 가져옵니다.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        // Koin 모듈을 설정하여, 인메모리 DB 대신 실제 앱의 DB를 사용하도록 합니다.
        loadKoinModules(module {
            single {
                Room.databaseBuilder(
                    appContext,
                    AppDatabase::class.java,
                    "app_database" // 실제 앱에서 사용하는 데이터베이스 이름
                ).allowMainThreadQueries().build()
            }
            single<BookmarkDao> {
                get<AppDatabase>().bookmarkDao()
            }
        })
    }

    @Test
    fun queryRealDatabase() {
        // When: ContentProvider를 통해 실제 데이터베이스의 북마크 데이터를 조회합니다.
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val contentResolver = context.contentResolver

        Log.d("BookmarkContentProviderTest", "Querying real database via ContentProvider...")
        val cursor = contentResolver.query(
            BookmarkContentProvider.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        // Then: cursor의 내용을 확인하고 로그로 출력합니다.
        assertNotNull("Cursor should not be null", cursor)

        if (cursor != null) {
            Log.i("BookmarkContentProviderTest", "Successfully retrieved cursor. Found ${cursor.count} bookmarks.")
            Log.d("BookmarkContentProviderTest", "--- Dumping Cursor Start ---")
            Log.d("BookmarkContentProviderTest", DatabaseUtils.dumpCursorToString(cursor))
            Log.d("BookmarkContentProviderTest", "--- Dumping Cursor End ---")
            cursor.close()
        }
    }
}
