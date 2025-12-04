package com.survivalcoding.gangnam2kiandroidstudy.data.model

data class Recipe(
    val id: Long,
    val name: String,
    val image: String,
    val chef: String,
    val time: String,
    val rating: Double,
)
