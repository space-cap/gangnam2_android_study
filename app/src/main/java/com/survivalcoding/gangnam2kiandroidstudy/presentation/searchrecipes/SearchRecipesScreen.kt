package com.survivalcoding.gangnam2kiandroidstudy.presentation.searchrecipes


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.survivalcoding.gangnam2kiandroidstudy.R
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.RecipeCardMedium
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.SearchField
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppTextStyles

@Composable
fun SearchRecipesScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchRecipesViewModel = viewModel(factory = SearchRecipesViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val recipes = uiState.recipes

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = "Search recipes",
            style = AppTextStyles.mediumTextBold,
            modifier = Modifier.padding(top = 10.dp, bottom = 17.dp),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchField(
                query = "",
                placeholder = "Search recipe",
                onQueryChange = {
                    viewModel.changeSearchText(it)
                },
                onSearch = {},
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        // viewModel.showBottomSheet()
                    }
                    .background(
                        color = AppColors.primary100,
                    )
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_setting_4_outline),
                    contentDescription = "setting icon",
                    tint = AppColors.white,
                    modifier = Modifier.size(20.dp),
                )
            }
        }

        Text(
            text = "Recent Search",
            style = AppTextStyles.mediumTextBold,
            modifier = Modifier.padding(top = 10.dp, bottom = 17.dp),
        )

        Spacer(modifier = Modifier.height(20.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
        ) {
            items(recipes) { recipe ->
                RecipeCardMedium(
                    recipe
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}


@Preview(showBackground = true)
@Composable
fun SearchRecipesScreenPreview() {
    SearchRecipesScreen()
}
