package com.survivalcoding.gangnam2kiandroidstudy.presentation.savedrecipes

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.survivalcoding.gangnam2kiandroidstudy.AppApplication
import com.survivalcoding.gangnam2kiandroidstudy.domain.usercase.GetSavedRecipesUseCase

class SavedRecipesViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SavedRecipesViewModel::class.java)) {
            val repository = (application as? AppApplication)?.recipeRepository
                ?: throw IllegalStateException("Application must be an instance of AppApplication")

            val getSavedRecipesUseCase = GetSavedRecipesUseCase(repository)

            @Suppress("UNCHECKED_CAST")
            return SavedRecipesViewModel(getSavedRecipesUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
