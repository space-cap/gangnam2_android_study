package com.survivalcoding.gangnam2kiandroidstudy.core.routing

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Route : NavKey {
    @Serializable
    data object Main : Route

    @Serializable
    data object SignIn : Route

    @Serializable
    data object SignUp : Route

    @Serializable
    data object Splash : Route

    @Serializable
    data object Home : Route

    @Serializable
    data object SavedRecipes : Route

    @Serializable
    data object Notifications : Route

    @Serializable
    data object Profile : Route

    @Serializable
    data class RecipeDetails(val recipeId: Long) : Route

    // companion object: Route와 관련된 추가적인 정보를 담는 공간입니다.
    companion object Companion {
        // allRoutes 라는 목록을 만들고, 하단 탭 바에 표시될 화면들(Home, SavedRecipes 등)을 담아두었습니다.
        // 이렇게 미리 목록을 만들어두면 나중에 화면을 구성할 때 편리하게 가져다 쓸 수 있습니다.
        val allRoutes: List<Route> = listOf(Home, SavedRecipes, Notifications, Profile)
    }
}
