package com.survivalcoding.gangnam2kiandroidstudy.core.di

import com.survivalcoding.gangnam2kiandroidstudy.data.repository.BookmarkRepositoryImpl
import com.survivalcoding.gangnam2kiandroidstudy.data.repository.IngredientRepositoryImpl
import com.survivalcoding.gangnam2kiandroidstudy.data.repository.ProcedureRepositoryImpl
import com.survivalcoding.gangnam2kiandroidstudy.data.repository.ProfileRepositoryImpl
import com.survivalcoding.gangnam2kiandroidstudy.data.repository.RecipeRepositoryImpl
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.BookmarkRepository
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.IngredientRepository
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.ProcedureRepository
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.ProfileRepository
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.RecipeRepository
import org.koin.dsl.module

/**
 * Koin의 의존성 주입(Dependency Injection)을 위한 모듈입니다.
 * 이 모듈은 데이터와 관련된 Repository들을 주입하는 방법을 Koin에게 알려줍니다.
 * Repository는 데이터 소스(네트워크, 데이터베이스 등)와 상호작용하는 역할을 합니다.
 */
val repositoryModule = module {
    // 'single' 키워드는 Koin에게 해당 클래스의 인스턴스를 '싱글톤'으로 생성하도록 지시합니다.
    // 싱글톤이란 앱 전체에서 단 하나의 인스턴스만 존재하며, 어디서든 동일한 인스턴스를 공유하여 사용한다는 의미입니다.
    // Repository는 여러 곳에서 동일한 데이터 소스를 참조해야 하므로 보통 싱글톤으로 관리합니다.

    // RecipeRepository 인터페이스를 요청하면 RecipeRepositoryImpl 구현체를 제공하라고 Koin에게 알려줍니다.
    // <RecipeRepository>는 요청할 때 사용할 타입(인터페이스), { RecipeRepositoryImpl(get()) }는 실제로 생성할 객체(구현 클래스)입니다.
    // get() 함수는 RecipeRepositoryImpl이 필요로 하는 다른 의존성(예: ApiService, Dao)을 Koin이 자동으로 주입해주도록 요청합니다.
    single<RecipeRepository> { RecipeRepositoryImpl(get(), get()) }

    // BookmarkRepository 인터페이스를 요청하면 BookmarkRepositoryImpl 구현체를 싱글톤으로 제공합니다.
    // 이 구현체는 생성자에서 BookmarkDao를 의존성으로 주입받습니다.
    single<BookmarkRepository> { BookmarkRepositoryImpl(get()) }

    // ProfileRepository 인터페이스를 요청하면 ProfileRepositoryImpl 구현체를 싱글톤으로 제공합니다.
    single<ProfileRepository> { ProfileRepositoryImpl() }

    // IngredientRepository 인터페이스를 요청하면 IngredientRepositoryImpl 구현체를 싱글톤으로 제공합니다.
    single<IngredientRepository> { IngredientRepositoryImpl() }

    // ProcedureRepository 인터페이스를 요청하면 ProcedureRepositoryImpl 구현체를 싱글톤으로 제공합니다.
    single<ProcedureRepository> { ProcedureRepositoryImpl() }
}
