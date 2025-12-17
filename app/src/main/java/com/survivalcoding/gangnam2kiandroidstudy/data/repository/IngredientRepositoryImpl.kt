package com.survivalcoding.gangnam2kiandroidstudy.data.repository

import com.survivalcoding.gangnam2kiandroidstudy.domain.model.Ingredient
import com.survivalcoding.gangnam2kiandroidstudy.domain.model.IngredientAmount
import com.survivalcoding.gangnam2kiandroidstudy.domain.model.UnitType
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.IngredientRepository

class IngredientRepositoryImpl : IngredientRepository {
    val mockIngredients = listOf(
        Ingredient(
            id = 1L,
            name = "Tomatoes",
            imageUrl = "https://cdn.pixabay.com/photo/2017/10/06/17/17/tomato-2823826_1280.jpg",
            amount = IngredientAmount(1.0, UnitType.PIECE),
        ),
        Ingredient(
            id = 1L,
            name = "Beef",
            imageUrl = "https://cdn.pixabay.com/photo/2016/01/21/18/08/meet-1154341_1280.png",
            amount = IngredientAmount(1.0, UnitType.PIECE),
        ),
        Ingredient(
            id = 1L,
            name = "Pork",
            imageUrl = "https://cdn.pixabay.com/photo/2019/12/20/14/44/meat-4708596_1280.jpg",
            amount = IngredientAmount(1.0, UnitType.PIECE),
        )
    )

    override suspend fun getIngredientsByRecipeId(recipeId: Long): List<Ingredient> {
        return mockIngredients.filter { it.id == recipeId }
    }
}
