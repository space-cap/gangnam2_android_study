package com.survivalcoding.gangnam2kiandroidstudy.data.provider

import android.database.DatabaseUtils
import android.util.Log
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.survivalcoding.gangnam2kiandroidstudy.data.dao.BookmarkDao
import com.survivalcoding.gangnam2kiandroidstudy.data.database.AppDatabase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

@RunWith(AndroidJUnit4::class)
class BookmarkContentProviderTest : KoinTest {

    private val bookmarkDao: BookmarkDao by inject()
    private val db: AppDatabase by inject()

    @Before
    fun setUp() {
        loadKoinModules(module {
            // 테스트용 인메모리 데이터베이스를 사용합니다.
            single {
                Room.inMemoryDatabaseBuilder(
                    InstrumentationRegistry.getInstrumentation().targetContext,
                    AppDatabase::class.java
                ).allowMainThreadQueries().build() // 테스트에서는 메인 스레드 쿼리 허용
            }
            // 데이터베이스에서 Dao를 가져옵니다.
            single<BookmarkDao> {
                get<AppDatabase>().bookmarkDao()
            }
        })
    }

    @Test
    fun query() = runBlocking {
        // Given: 테스트용 데이터 삽입
        val recipeId = 1L
        bookmarkDao.insert(recipeId)

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val contentResolver = context.contentResolver

        // When: contentProvider를 통해 북마크 데이터를 조회합니다.
        val cursor = contentResolver.query(
            BookmarkContentProvider.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        // Then: cursor가 null이 아닌지 확인하고 내용을 로그로 출력합니다.
        assertNotNull(cursor)
        assertTrue(cursor!!.count > 0)

        // Cursor 내용을 Logcat에 출력합니다.
        Log.d("BookmarkContentProviderTest", DatabaseUtils.dumpCursorToString(cursor))

        // cursor를 올바르게 닫아줍니다.
        cursor.close()
    }
}
