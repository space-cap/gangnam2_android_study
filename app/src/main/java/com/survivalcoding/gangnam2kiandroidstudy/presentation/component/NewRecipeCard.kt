package com.survivalcoding.gangnam2kiandroidstudy.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.survivalcoding.gangnam2kiandroidstudy.R
import com.survivalcoding.gangnam2kiandroidstudy.domain.model.Recipe
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppTextStyles
import com.survivalcoding.gangnam2kiandroidstudy.utils.orPreview

@Composable
fun NewRecipeCard(
    recipe: Recipe,
    modifier: Modifier = Modifier,
    onClick: (Long) -> Unit = {},
) {
    Box(
        modifier = modifier
            .size(251.dp, 127.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick(recipe.id) },
        contentAlignment = Alignment.BottomCenter,
    ) {
        Box(
            modifier = Modifier
                .size(251.dp, 95.dp)
                .background(color = AppColors.white, shape = RoundedCornerShape(10.dp))
                .padding(horizontal = 9.3.dp, vertical = 10.dp),
        ) {
            Column {
                Text(
                    text = recipe.name,
                    style = AppTextStyles.smallTextRegular,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(end = 102.26.dp),
                )

                Row(
                    modifier = Modifier.padding(top = 5.dp, bottom = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                ) {
                    val rating = recipe.rating
                    val filledStars = rating.toInt().coerceIn(0, 5)
                    val hasEmptyStar = rating > filledStars && filledStars < 5

                    repeat(filledStars) {
                        Image(
                            painter = painterResource(R.drawable.bold_star),
                            contentDescription = "star ${it + 1}",
                            colorFilter = ColorFilter.tint(color = AppColors.rating),
                            modifier = Modifier.size(12.dp),
                        )
                    }

                    if (hasEmptyStar) {
                        Image(
                            painter = painterResource(R.drawable.outline_star),
                            contentDescription = "empty star",
                            colorFilter = ColorFilter.tint(color = AppColors.rating),
                            modifier = Modifier.size(12.dp),
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        AsyncImage(
                            model = recipe.chefImageUrl.orPreview,
                            contentDescription = "recipe image",
                            modifier = Modifier
                                .size(25.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop,
                        )
                        Text(
                            text = "By ${recipe.chef}",
                            style = AppTextStyles.smallTextRegular,
                            color = AppColors.gray3,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(R.drawable.outline_timer),
                            contentDescription = "timer image",
                            colorFilter = ColorFilter.tint(color = AppColors.gray4),
                            modifier = Modifier.size(17.dp),
                        )
                        Text(
                            text = "${recipe.time} min",
                            style = AppTextStyles.smallTextRegular.copy(color = AppColors.gray4),
                        )
                    }
                }
            }
        }

        AsyncImage(
            model = recipe.image.orPreview,
            contentDescription = "recipe image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(end = 9.3.dp)
                .size(86.dp)
                .clip(CircleShape)
                .align(Alignment.TopEnd),
        )
    }
}