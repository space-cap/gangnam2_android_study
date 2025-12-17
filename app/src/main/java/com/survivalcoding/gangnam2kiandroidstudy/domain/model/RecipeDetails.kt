package com.survivalcoding.gangnam2kiandroidstudy.domain.model

data class RecipeDetails(
    val recipe: Recipe?,
    val profile: Profile,
    val ingredients: List<Ingredient> = emptyList(),
    val procedures: List<Procedure> = emptyList(),
)
