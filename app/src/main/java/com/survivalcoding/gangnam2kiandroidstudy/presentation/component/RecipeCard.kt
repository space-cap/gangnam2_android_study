package com.survivalcoding.gangnam2kiandroidstudy.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.survivalcoding.gangnam2kiandroidstudy.R
import com.survivalcoding.gangnam2kiandroidstudy.data.model.Recipe
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppTextStyles

@Composable
fun RecipeCard(
    recipe: Recipe,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .size(width = 315.dp, height = 150.dp)
            .padding(all = 10.dp)
            .clickable(onClick = onClick),
        ) {

        AsyncImage(
            model = recipe.image,
            contentDescription = "레시피 이미지",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp))
        )


        Row(


        ) {
            Column(
                modifier = Modifier.height(150.dp),
                verticalArrangement = Arrangement.Bottom,
            ) {
                Text(
                    text = recipe.name,
                    style = AppTextStyles.smallTextBold,
                    color = AppColors.white,
                    maxLines = 2,
                    modifier = Modifier.size(200.dp, 42.dp),
                )
                Text(
                    text = "By " + recipe.chef,
                    style = AppTextStyles.smallerTextSmallLabel,
                    color = AppColors.gray4,
                )

            }


            Column(
                modifier = Modifier.fillMaxWidth(),

                ) {
                Row(
                    modifier = Modifier
                        .size(50.dp, 16.dp)
                        .padding(horizontal = 7.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(AppColors.secondary20),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_star),
                        contentDescription = "star",
                        tint = Color.Yellow,
                        modifier = Modifier.size(8.dp),
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = recipe.rating.toString(),
                        style = AppTextStyles.smallerTextSmallLabel,
                        color = AppColors.black,
                    )

                }
                Spacer(modifier = Modifier.height(5.dp))

                Row(
                    modifier = Modifier.fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_timer),
                        contentDescription = "time",
                        tint = AppColors.gray2,
                        modifier = Modifier.size(8.dp),
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = recipe.time,
                        style = AppTextStyles.smallerTextSmallLabel,
                        color = AppColors.gray2,
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        painter = painterResource(R.drawable.bold_bookmark_active),
                        contentDescription = "food",
                        tint = AppColors.gray2,

                        )

                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeCardPreview() {
    RecipeCard(
        recipe = Recipe(
            id = 1,
            name = "Traditional spare ribs baked",
            image = "https://cdn.pixabay.com/photo/2017/11/10/15/04/steak-2936531_1280.jpg",
            chef = "steve.kim",
            time = "30분",
            rating = 4.5
        )
    )
}
