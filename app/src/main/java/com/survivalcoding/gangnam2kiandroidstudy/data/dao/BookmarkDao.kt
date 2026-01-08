package com.survivalcoding.gangnam2kiandroidstudy.data.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    // 단순 조회: 빠르게 끝나므로 일반 fun 사용
    @Query("SELECT recipeId FROM bookmark")
    fun getIds(): Flow<List<Long>>

    // 데이터 삽입: 디스크 I/O 작업으로 시간이 걸릴 수 있으므로 suspend fun 사용
    @Query("INSERT INTO bookmark (recipeId) VALUES (:recipeId)")
    suspend fun insert(recipeId: Long)

    @Query("DELETE FROM bookmark WHERE recipeId = :recipeId")
    suspend fun delete(recipeId: Long)

    @Query("SELECT EXISTS (SELECT 1 FROM bookmark WHERE recipeId = :recipeId)")
    suspend fun isBookmarked(recipeId: Long): Boolean

    /**
     * ContentProvider를 위해 모든 북마크 데이터를 Cursor 형태로 조회합니다.
     */
    @Query("SELECT * FROM bookmark")
    fun selectAllCursor(): Cursor

    /**
     * ContentProvider를 위해 특정 ID의 북마크를 Cursor 형태로 조회합니다.
     * @param id 조회할 북마크의 ID
     * @return 해당 북마크 데이터를 담은 Cursor
     */
    @Query("SELECT * FROM bookmark WHERE recipeId = :id")
    fun selectByIdCursor(id: Long): Cursor
}
