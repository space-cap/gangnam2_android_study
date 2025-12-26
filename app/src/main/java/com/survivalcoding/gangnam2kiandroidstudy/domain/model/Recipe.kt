package com.survivalcoding.gangnam2kiandroidstudy.domain.model

data class Recipe(
    val id: Long,
    val name: String,
    val image: String,
    val chef: String,
    val time: String,
    val rating: Double,
    val category: String,
    val serve: Int = 0,
    val chefImageUrl: String = "",
    val isSaved: Boolean = false,
    val shareUrl: String = "share_url",
    //val description: String,
    //val reviewCount: Int,
    //val ingredients: List<Ingredient>,
    //val procedures: List<Procedure>,
)
