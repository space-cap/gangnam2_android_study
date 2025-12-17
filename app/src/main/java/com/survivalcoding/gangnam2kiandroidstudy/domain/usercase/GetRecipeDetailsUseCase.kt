package com.survivalcoding.gangnam2kiandroidstudy.domain.usercase

import com.survivalcoding.gangnam2kiandroidstudy.domain.model.RecipeDetails
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.IngredientRepository
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.ProcedureRepository
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.ProfileRepository
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.RecipeRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetRecipeDetailsUseCase(
    private val recipeRepository: RecipeRepository,
    private val profileRepository: ProfileRepository,
    private val ingredientRepository: IngredientRepository,
    private val procedureRepository: ProcedureRepository,
) {
    suspend operator fun invoke(recipeId: Long): RecipeDetails {

        val result = coroutineScope {
            val recipe = async { recipeRepository.getRecipe(recipeId) }
            val profile = async { profileRepository.getProfileByRecipeId(recipeId) }
            val ingredients = async { ingredientRepository.getIngredientsByRecipeId(recipeId) }
            val procedures = async { procedureRepository.getProceduresByRecipeId(recipeId) }

            RecipeDetails(
                recipe = recipe.await(),
                profile = profile.await(),
                ingredients = ingredients.await(),
                procedures = procedures.await(),
            )
        }

        return result
    }
}
