package com.survivalcoding.gangnam2kiandroidstudy.core.di


import androidx.room.Room
import com.survivalcoding.gangnam2kiandroidstudy.data.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val roomModule = module {

    // AppDatabase의 싱글톤 인스턴스를 제공합니다.
    single {
        Room.databaseBuilder(
            androidContext(), // Koin에서 제공하는 Context
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    // AppDatabase를 통해 BookmarkDao 인스턴스를 제공합니다.
    single {
        // get() 함수를 통해 위에서 정의한 AppDatabase 인스턴스를 주입받습니다.
        get<AppDatabase>().bookmarkDao()
    }
}
