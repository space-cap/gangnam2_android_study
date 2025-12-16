package com.survivalcoding.gangnam2kiandroidstudy.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.survivalcoding.gangnam2kiandroidstudy.R
import com.survivalcoding.gangnam2kiandroidstudy.domain.model.Recipe
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppTextStyles
import java.util.Locale

@Composable
fun RecipeCardMedium(
    recipe: Recipe,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(150.dp) // Changed from fillMaxWidth to a fixed size
            .clip(RoundedCornerShape(12.dp))
    ) {

        // Background Image
        AsyncImage(
            model = recipe.image,
            contentDescription = recipe.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )

        // Gradient overlay for text readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.8f) // Darker at the bottom
                        ),
                        startY = 200f // Start gradient from about 1/3 down
                    )
                )
        )

        // Text content aligned to the bottom-left
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp)
        ) {
            Text(
                text = recipe.name,
                style = AppTextStyles.smallTextBold.copy(color = AppColors.white, fontSize = 16.sp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "By ${recipe.chef}",
                style = AppTextStyles.smallerTextRegular.copy(
                    color = AppColors.white.copy(alpha = 0.9f),
                    fontSize = 12.sp
                )
            )
        }

        // Rating aligned to the top-right
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .background(
                    color = AppColors.secondary20,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(vertical = 4.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_star_6),
                contentDescription = "average rating",
                tint = AppColors.secondary100,
                modifier = Modifier.size(12.dp),
            )
            Spacer(Modifier.width(4.dp))

            Text(
                text = String.format(Locale.US, "%.1f", recipe.rating),
                style = AppTextStyles.smallTextBold.copy(
                    fontSize = 12.sp,
                    color = Color.Black.copy(alpha = 0.8f),
                    fontWeight = FontWeight.Bold
                ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeCardMediumPreview() {
    RecipeCardMedium(
        recipe = sampleRecipe
    )
}

val sampleRecipe =
    Recipe(
        id = 1,
        name = "Traditional spare ribs baked",
        image = "https://cdn.pixabay.com/photo/2017/11/10/15/04/steak-2936531_1280.jpg",
        chef = "Chef John",
        time = "20 min",
        rating = 4.0,
        category = "Indian",
    )
