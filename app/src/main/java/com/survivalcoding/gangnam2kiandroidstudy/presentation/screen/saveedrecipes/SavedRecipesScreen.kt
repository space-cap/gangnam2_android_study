package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.saveedrecipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.RecipeCard
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppTextStyles

@Composable
fun SavedRecipesScreen(
    modifier: Modifier = Modifier,
    viewModel: SavedRecipesViewModel = viewModel(factory = SavedRecipesViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier.padding(
            paddingValues = PaddingValues(
                horizontal = 20.dp,
                vertical = 20.dp
            )
        ),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Saved recipes",
                style = AppTextStyles.mediumTextBold.copy(color = AppColors.black),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.height(10.dp))


            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.recipes) { recipe ->
                        RecipeCard(
                            name = recipe.name,
                            imageUrl = recipe.image,
                            chef = recipe.chef,
                            time = recipe.time,
                            rating = recipe.rating
                        )

                        Spacer(Modifier.height(20.dp))
                    }
                }
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun SavedRecipesScreenPreview() {
    SavedRecipesScreen()
}
