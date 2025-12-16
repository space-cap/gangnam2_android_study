package com.survivalcoding.gangnam2kiandroidstudy.data.mapper

import com.survivalcoding.gangnam2kiandroidstudy.data.dto.RecipeDto
import com.survivalcoding.gangnam2kiandroidstudy.domain.model.Recipe

fun RecipeDto.toRecipe(): Recipe {
    return Recipe(
        id = (id ?: 0).toLong(),
        name = name ?: "",
        image = image ?: "",
        chef = chef ?: "",
        time = time ?: "",
        rating = rating ?: 0.0,
        category = category ?: "",
    )
}