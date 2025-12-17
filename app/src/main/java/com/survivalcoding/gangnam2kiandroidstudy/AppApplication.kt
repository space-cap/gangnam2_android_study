package com.survivalcoding.gangnam2kiandroidstudy

import android.app.Application
import com.survivalcoding.gangnam2kiandroidstudy.data.datasource.RecipeDataSource
import com.survivalcoding.gangnam2kiandroidstudy.data.datasource.RecipeDataSourceImpl
import com.survivalcoding.gangnam2kiandroidstudy.data.repository.IngredientRepositoryImpl
import com.survivalcoding.gangnam2kiandroidstudy.data.repository.ProcedureRepositoryImpl
import com.survivalcoding.gangnam2kiandroidstudy.data.repository.ProfileRepositoryImpl
import com.survivalcoding.gangnam2kiandroidstudy.data.repository.RecipeRepositoryImpl
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.IngredientRepository
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.ProcedureRepository
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.ProfileRepository
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.RecipeRepository
import com.survivalcoding.gangnam2kiandroidstudy.domain.usercase.GetRecipeDetailsUseCase
import com.survivalcoding.gangnam2kiandroidstudy.presentation.todo.TodoDataSource
import com.survivalcoding.gangnam2kiandroidstudy.presentation.todo.TodoDataSourceImpl
import com.survivalcoding.gangnam2kiandroidstudy.presentation.todo.TodoRepository
import com.survivalcoding.gangnam2kiandroidstudy.presentation.todo.TodoRepositoryImpl

class AppApplication : Application() {

    private val todoDataSource: TodoDataSource by lazy {
        TodoDataSourceImpl()
    }

    val todoRepository: TodoRepository by lazy {
        TodoRepositoryImpl(todoDataSource)
    }

    private val recipeDataSource: RecipeDataSource by lazy {
        RecipeDataSourceImpl(this)
    }

    val recipeRepository: RecipeRepository by lazy {
        RecipeRepositoryImpl(recipeDataSource)
    }

    val profileRepository: ProfileRepository by lazy { ProfileRepositoryImpl() }
    val ingredientRepository: IngredientRepository by lazy { IngredientRepositoryImpl() }
    val procedureRepository: ProcedureRepository by lazy { ProcedureRepositoryImpl() }

    val getRecipeDetailsUseCase: GetRecipeDetailsUseCase by lazy {
        GetRecipeDetailsUseCase(
            recipeRepository,
            profileRepository,
            ingredientRepository,
            procedureRepository,
            )
    }
}
