package com.survivalcoding.gangnam2kiandroidstudy.domain.model

enum class UnitType(val label: String) {
    GRAM("g"),
    MILLILITER("ml"),
    PIECE("개"),
    CUP("cup"),
    TABLESPOON("tbsp"),
    TEASPOON("tsp")
}

data class IngredientAmount(
    val value: Double,      // 0.5, 1.0, 300.0 등
    val unit: UnitType
)

data class Ingredient(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val amount: IngredientAmount = IngredientAmount(0.0, UnitType.PIECE),
)
