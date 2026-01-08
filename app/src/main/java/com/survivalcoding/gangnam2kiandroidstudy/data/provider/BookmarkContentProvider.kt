package com.survivalcoding.gangnam2kiandroidstudy.data.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.survivalcoding.gangnam2kiandroidstudy.data.dao.BookmarkDao
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BookmarkContentProvider : ContentProvider(), KoinComponent {

    // Koin을 통해 BookmarkDao 의존성 주입
    private val bookmarkDao: BookmarkDao by inject()

    companion object {
        // authorities는 AndroidManifest.xml에 정의된 값과 일치해야 함
        private const val AUTHORITY = "com.survivalcoding.gangnam2kiandroidstudy.provider"
        private val BASE_CONTENT_URI = Uri.parse("content://$AUTHORITY")

        // 테이블 경로 정의
        private const val PATH_BOOKMARKS = "bookmarks"
        val CONTENT_URI: Uri = BASE_CONTENT_URI.buildUpon().appendPath(PATH_BOOKMARKS).build()

        // URI 매칭을 위한 코드
        private const val BOOKMARKS = 100 // 모든 북마크
        private const val BOOKMARK_ID = 101 // 특정 ID의 북마크

        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            // content://com.survivalcoding.gangnam2kiandroidstudy.provider/bookmarks
            addURI(AUTHORITY, PATH_BOOKMARKS, BOOKMARKS)
            // content://com.survivalcoding.gangnam2kiandroidstudy.provider/bookmarks/#
            addURI(AUTHORITY, "$PATH_BOOKMARKS/#", BOOKMARK_ID)
        }
    }

    override fun onCreate(): Boolean {
        // ContentProvider가 생성될 때 호출됩니다.
        // 여기서는 Koin이 초기화되도록 `true`만 반환하면 충분합니다.
        return true
    }

    /**
     * 외부 앱에서 데이터를 조회(SELECT)할 때 호출됩니다.
     */
    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        val context = context ?: return null
        val cursor: Cursor = when (uriMatcher.match(uri)) {
            // 모든 북마크를 조회하는 경우
            BOOKMARKS -> {
                bookmarkDao.selectAllCursor()
            }
            // 특정 ID의 북마크를 조회하는 경우
            BOOKMARK_ID -> {
                val id = uri.lastPathSegment?.toLongOrNull() ?: -1
                bookmarkDao.selectByIdCursor(id)
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }

        // Cursor가 변경되었을 때 알림을 받을 수 있도록 설정
        cursor.setNotificationUri(context.contentResolver, uri)
        return cursor
    }

    /**
     * MIME 타입을 반환합니다.
     */
    override fun getType(uri: Uri): String? {
        return when (uriMatcher.match(uri)) {
            BOOKMARKS -> "vnd.android.cursor.dir/$AUTHORITY.$PATH_BOOKMARKS"
            BOOKMARK_ID -> "vnd.android.cursor.item/$AUTHORITY.$PATH_BOOKMARKS"
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    // --- 아래 메서드들은 현재 요구사항(읽기 전용)에서는 구현하지 않아도 됩니다. ---
    // 만약 데이터 삽입, 수정, 삭제 기능까지 제공하려면 각 메서드를 구현해야 합니다.

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        // 읽기 전용으로 제공할 경우, 지원하지 않음을 명시
        throw UnsupportedOperationException("Insert is not supported")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        throw UnsupportedOperationException("Delete is not supported")
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        throw UnsupportedOperationException("Update is not supported")
    }
}
