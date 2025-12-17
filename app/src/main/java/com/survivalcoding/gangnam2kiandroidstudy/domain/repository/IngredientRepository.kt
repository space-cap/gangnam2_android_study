package com.survivalcoding.gangnam2kiandroidstudy.domain.repository

import com.survivalcoding.gangnam2kiandroidstudy.domain.model.Ingredient

interface IngredientRepository {
    suspend fun getIngredientsByRecipeId(recipeId: Long): List<Ingredient>
}