package com.survivalcoding.gangnam2kiandroidstudy.presentation.recipedetails

import com.survivalcoding.gangnam2kiandroidstudy.domain.model.Ingredient
import com.survivalcoding.gangnam2kiandroidstudy.domain.model.Procedure
import com.survivalcoding.gangnam2kiandroidstudy.domain.model.Profile
import com.survivalcoding.gangnam2kiandroidstudy.domain.model.Recipe

data class RecipeDetailsState(
    val recipe: Recipe? = null,
    val isLoading: Boolean = false,
    val profile: Profile? = null,
    var selectedTabIndex: Int = 0, // 0: ingredients, 1: procedures
    val reviewCount: Int = 0,
    val ingredients: List<Ingredient> = emptyList(),
    val procedures: List<Procedure> = emptyList(),
)
