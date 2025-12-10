package com.survivalcoding.gangnam2kiandroidstudy.presentation.searchrecipes

enum class TimeFilterType(val label: String) {
    ALL("All"),
    NEWEST("Newest"),
    OLDEST("Oldest"),
    POPULARITY("Popularity"),
}

enum class RateFilterType(val label: String) {
    FIVE("5"),
    FOUR("4"),
    THREE("3"),
    TWO("2"),
    ONE("1"),
}

enum class CategoryFilterType(val label: String) {
    ALL("All"),
    CEREAL("Cereal"),
    VEGETABLES("Vegetables"),
    DINNER("Dinner"),
    CHINESE("Chinese"),
    LOCAL_DISH("Local Dish"),
    FRUIT("Fruit"),
    BREAKFAST("Breakfast"),
    SPANISH("Spanish"),
    LUNCH("Lunch"),
}

data class RecipeFilterState(
    val time: String = "ALL",
    val rate: Int = 5,
    val category: String = "ALL",
)
