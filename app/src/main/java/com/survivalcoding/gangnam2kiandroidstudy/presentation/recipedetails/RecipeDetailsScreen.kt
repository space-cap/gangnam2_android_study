package com.survivalcoding.gangnam2kiandroidstudy.presentation.recipedetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.survivalcoding.gangnam2kiandroidstudy.R
import com.survivalcoding.gangnam2kiandroidstudy.domain.model.Ingredient
import com.survivalcoding.gangnam2kiandroidstudy.domain.model.IngredientAmount
import com.survivalcoding.gangnam2kiandroidstudy.domain.model.Procedure
import com.survivalcoding.gangnam2kiandroidstudy.domain.model.Profile
import com.survivalcoding.gangnam2kiandroidstudy.domain.model.Recipe
import com.survivalcoding.gangnam2kiandroidstudy.domain.model.UnitType
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.IngredientItem
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.ProcedureItem
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.RecipeCard
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.RecipeDetailsDropdownMenu
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.SmallButton
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.Tabs
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppTextStyles
import com.survivalcoding.gangnam2kiandroidstudy.utils.orPreview
import kotlinx.collections.immutable.toImmutableList

@Composable
fun RecipeDetailsScreen(
    modifier: Modifier = Modifier,
    uiState: RecipeDetailsState = RecipeDetailsState(),
    onTabClick: (Int) -> Unit = {},
    onBackClick: () -> Unit = {},
    onAction: (RecipeDetailsAction) -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp)
            .statusBarsPadding()
            .navigationBarsPadding(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Icon(
                painter = painterResource(R.drawable.outline_arrow_right),
                contentDescription = "back icon",
                modifier = Modifier
                    .size(20.dp)
                    .rotate(180f)
                    .clickable { onBackClick() },
            )
            IconButton(
                onClick = { expanded = !expanded },
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_more),
                    contentDescription = "more icon",
                    modifier = Modifier
                        .size(24.dp),
                )
            }

            uiState.recipe?.let { recipe ->
                RecipeDetailsDropdownMenu(
                    isExpanded = uiState.isMenuVisible,
                    isSaved = uiState.recipe.isSaved,
                    onDismissRequest = {
                        onAction(RecipeDetailsAction.OnMenuDismissRequest)
                    },
                    onShareClick = {
                        onAction(RecipeDetailsAction.OnShareClick)
                    },
                    onRateClick = {
                        onAction(RecipeDetailsAction.OnRateClick)
                    },
                    onReviewClick = {
                        onAction(RecipeDetailsAction.OnReviewClick(recipe.id))
                    },
                    onBookmarkClick = {
                        onAction(RecipeDetailsAction.OnBookmarkClick(recipe.id))
                    },
                )
            }

        }


        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            uiState.recipe?.let {
                RecipeCard(
                    recipe = it,
                )
            }

            Row(
                modifier = Modifier.padding(horizontal = 5.dp),
            ) {
                Text(
                    text = uiState.recipe?.name ?: "",
                    style = AppTextStyles.smallTextBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 18.dp),
                )

                Text(
                    text = "(${uiState.reviewCount} Reviews)",
                    style = AppTextStyles.smallTextBold.copy(AppColors.gray3),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, end = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AsyncImage(
                    model = uiState.profile?.imageUrl?.orPreview,
                    contentDescription = "profile image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                ) {
                    Text(
                        text = uiState.profile?.name ?: "",
                        style = AppTextStyles.smallTextBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(1.dp),
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.bold_location),
                            contentDescription = "location icon",
                            tint = AppColors.primary80,
                            modifier = Modifier.size(17.dp),
                        )
                        Text(
                            text = uiState.profile?.address ?: "",
                            style = AppTextStyles.smallTextBold.copy(AppColors.gray3),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }

            SmallButton(
                text = "Follow",
                modifier = Modifier.width(100.dp),
            ) {}
        }


        Tabs(
            labels = listOf("Ingredient", "Procedure").toImmutableList(),
            selectedIndex = uiState.selectedTabIndex,
            onTabSelected = onTabClick,
        )


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 22.dp, bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_timer_outline),
                    contentDescription = "timer icon",
                    tint = AppColors.gray3,
                    modifier = Modifier.size(17.dp),
                )
                Text(
                    text = "${uiState.recipe?.serve} serve",
                    style = AppTextStyles.smallTextRegular.copy(AppColors.gray3),
                )
            }

            if (uiState.selectedTabIndex == 0) {
                Text(
                    text = "${uiState.ingredients.size} items",
                    style = AppTextStyles.smallTextRegular.copy(AppColors.gray3),
                )
            }

            if (uiState.selectedTabIndex == 1) {
                Text(
                    text = "${uiState.procedures.size} steps",
                    style = AppTextStyles.smallTextRegular.copy(AppColors.gray3),
                )
            }

        }

        if (uiState.selectedTabIndex == 0 && uiState.ingredients.isNotEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(
                    items = uiState.ingredients,
                    key = { it.name },
                ) {
                    IngredientItem(ingredient = it)
                }
            }
        }

        if (uiState.selectedTabIndex == 1 && uiState.procedures.isNotEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(
                    items = uiState.procedures,
                    key = { it.step },
                ) {
                    ProcedureItem(procedure = it)
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun RecipeDetailsScreenPreview() {
    val recipe = Recipe(
        id = 1,
        name = "Kimchi Fried Rice",
        image = "https://cdn.pixabay.com/photo/2017/11/10/15/04/steak-2936531_1280.jpg",
        chef = "Chef John",
        time = "20 min",
        rating = 4.0,
        category = "Korean",
    )

    val profile = Profile(
        id = 1,
        name = "Afuwape Abiodun",
        imageUrl = "https://picsum.photos/id/259/200/300",
        address = "Lagos, Nigeria",
        email = "lee@naver.com",
    )

    val ingredients = listOf(
        Ingredient(
            id = 1,
            name = "Tomato",
            imageUrl = "",
            amount = IngredientAmount(1.0, UnitType.PIECE),
        ),
    )

    val procedures = listOf(
        Procedure(
            recipeId = 1,
            step = 1,
            content = "Lorem Ipsum tempor incididunt ut labore et dolore,in voluptate velit esse cillum dolore eu fugiat nulla pariatur?",
        ),
    )

    RecipeDetailsScreen(
        uiState = RecipeDetailsState(
            recipe = recipe,
            profile = profile,
            reviewCount = 1,
            ingredients = ingredients,
            procedures = procedures,
        )
    )
}
