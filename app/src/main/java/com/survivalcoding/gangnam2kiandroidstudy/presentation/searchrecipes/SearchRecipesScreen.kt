package com.survivalcoding.gangnam2kiandroidstudy.presentation.searchrecipes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SearchRecipesScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchRecipesViewModel = viewModel(factory = SearchRecipesViewModel.Factory)
) {

}

@Preview(showBackground = true)
@Composable
fun SearchRecipesScreenPreview() {
    SearchRecipesScreen()
}

