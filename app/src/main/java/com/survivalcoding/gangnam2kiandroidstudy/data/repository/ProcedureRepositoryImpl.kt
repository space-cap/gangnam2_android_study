package com.survivalcoding.gangnam2kiandroidstudy.data.repository

import com.survivalcoding.gangnam2kiandroidstudy.domain.model.Procedure
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.ProcedureRepository

class ProcedureRepositoryImpl: ProcedureRepository {
    val mockProcedures = listOf(
        Procedure(
            recipeId = 1L,
            step = 1,
            content = "Preheat the oven to 350°F (175°C).",
        ),
        Procedure(
            recipeId = 1L,
            step = 2,
            content = "Remove the membrane from the back of the ribs if it is still attached.",
        ),
        Procedure(
            recipeId = 1L,
            step = 3,
            content = "Season the ribs generously with salt, pepper, and your favorite rub.",
        )
    )

    override suspend fun getProceduresByRecipeId(recipeId: Long): List<Procedure> {
        return mockProcedures.filter { it.recipeId == recipeId }
    }
}
