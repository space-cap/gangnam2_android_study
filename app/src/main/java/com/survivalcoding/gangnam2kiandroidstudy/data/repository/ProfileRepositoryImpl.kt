package com.survivalcoding.gangnam2kiandroidstudy.data.repository

import com.survivalcoding.gangnam2kiandroidstudy.domain.model.Profile
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.ProfileRepository

class ProfileRepositoryImpl: ProfileRepository {
    val mockProfiles = listOf(
        Profile(
            id = 1L,
            name = "Chef John",
            imageUrl = "https://cdn.pixabay.com/photo/2022/10/19/01/02/woman-7531315_1280.png",
            recipeCount = 45,
            followerCount = 15200,
            followingCount = 150,
            job = "Head Chef",
            biography = "West African cuisine specialist. Bringing Lagos flavors to the world.",
            address = "Lagos, Nigeria",
        )
    )

    override suspend fun getProfileByRecipeId(recipeId: Long): Profile {
        return mockProfiles.first()
    }
}
