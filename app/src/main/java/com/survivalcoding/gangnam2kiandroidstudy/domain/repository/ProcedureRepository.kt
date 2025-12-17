package com.survivalcoding.gangnam2kiandroidstudy.domain.repository

import com.survivalcoding.gangnam2kiandroidstudy.domain.model.Procedure

interface ProcedureRepository {
    suspend fun getProceduresByRecipeId(recipeId: Long): List<Procedure>
}