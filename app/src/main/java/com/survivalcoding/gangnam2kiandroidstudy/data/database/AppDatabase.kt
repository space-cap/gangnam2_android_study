package com.survivalcoding.gangnam2kiandroidstudy.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.survivalcoding.gangnam2kiandroidstudy.data.dao.BookmarkDao
import com.survivalcoding.gangnam2kiandroidstudy.data.entity.BookmarkEntity

/**
 * 앱의 데이터베이스를 정의하는 추상 클래스입니다.
 * 이 클래스는 Room 라이브러리에 의해 구현됩니다.
 *
 * @Database 어노테이션은 이 클래스가 Room 데이터베이스임을 나타냅니다.
 * - entities: 데이터베이스에 포함될 엔티티(테이블)의 목록입니다.
 * - version: 데이터베이스의 버전입니다. 스키마 변경 시 이 값을 올려야 합니다.
 */
@Database(entities = [BookmarkEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    /**
     * Room이 BookmarkDao의 구현체를 제공할 수 있도록 하는 추상 메서드입니다.
     * 이 메서드를 통해 데이터베이스와 상호작용할 수 있습니다.
     *
     * @return BookmarkDao 인스턴스
     */
    abstract fun bookmarkDao(): BookmarkDao
}
