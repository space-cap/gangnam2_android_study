package com.survivalcoding.gangnam2kiandroidstudy.core.di

import com.survivalcoding.gangnam2kiandroidstudy.presentation.home.HomeViewModel
import com.survivalcoding.gangnam2kiandroidstudy.presentation.recipedetails.RecipeDetailsViewModel
import com.survivalcoding.gangnam2kiandroidstudy.presentation.savedrecipes.SavedRecipesViewModel
import com.survivalcoding.gangnam2kiandroidstudy.presentation.searchrecipes.SearchRecipesViewModel
import com.survivalcoding.gangnam2kiandroidstudy.presentation.splash.SplashViewModel
import com.survivalcoding.gangnam2kiandroidstudy.presentation.todo.TodoViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin의 의존성 주입(Dependency Injection)을 위한 모듈입니다.
 * 이 모듈은 ViewModel들을 주입하는 방법을 Koin에게 알려줍니다.
 * 앱의 다른 부분에서 ViewModel을 필요로 할 때, Koin이 이 모듈의 정의를 보고
 * 어떻게 ViewModel 인스턴스를 생성하고 제공해야 하는지 알게 됩니다.
 */
val viewModelModule = module {
    // viewModel 키워드는 Koin에게 ViewModel을 생성하도록 지시합니다.
    // { HomeViewModel(get()) } 부분은 HomeViewModel을 생성하는 방법을 정의합니다.
    // get() 함수는 Koin에게 HomeViewModel이 필요로 하는 다른 의존성(dependency)을
    // 자동으로 찾아서 주입해달라고 요청하는 것입니다.
    // 예를 들어, HomeViewModel이 RecipeRepository를 필요로 한다면,
    // 다른 모듈(예: repositoryModule)에 정의된 RecipeRepository를 찾아서 넣어줍니다.
    viewModel { HomeViewModel(get(), get()) }

    // RecipeDetailsViewModel을 주입하기 위한 정의입니다.
    // get()을 통해 필요한 의존성을 자동으로 주입받습니다.
    viewModel { RecipeDetailsViewModel(get(), get()) }

    // SavedRecipesViewModel을 주입하기 위한 정의입니다.
    viewModel { SavedRecipesViewModel(get()) }

    // SearchRecipesViewModel을 주입하기 위한 정의입니다.
    viewModel { SearchRecipesViewModel(get()) }

    viewModel { SplashViewModel(get()) }

    // TodoViewModel을 주입하기 위한 정의입니다.
    // get()이 두 번 사용된 것은 TodoViewModel이 두 개의 의존성을 필요로 한다는 의미입니다.
    // Koin이 각각의 의존성을 순서에 맞게 찾아서 주입해줍니다.
    viewModel { TodoViewModel(get(), get()) }
}
