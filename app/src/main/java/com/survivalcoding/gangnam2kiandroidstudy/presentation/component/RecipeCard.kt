package com.survivalcoding.gangnam2kiandroidstudy.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.survivalcoding.gangnam2kiandroidstudy.R
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppTextStyles

@Composable
fun RecipeCard(
    name: String,
    imageUrl: String,
    chef: String,
    time: String,
    rating: Double,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .height(150.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick),




    ) {

        // Background Image
        AsyncImage(
            model = imageUrl,
            contentDescription = name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize(),
        )

        // 글씨 뒤 반투명 오버레이
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color(0x80000000) // 반투명 블랙
                        ),
                        startY = 120f // 시작점
                    )
                )
        )

        Row(
            modifier = Modifier
                .matchParentSize()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {

            // Left
            Column(
                modifier = Modifier.width(200.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = name,
                    style = AppTextStyles.smallTextBold.copy(color = AppColors.white),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(end = 15.dp)
                )
                Text(
                    text = "By $chef",
                    style = AppTextStyles.smallerTextRegular.copy(
                        color = AppColors.white,
                        fontSize = 8.sp
                    )
                )
            }

            // Right (Rating + Time + Bookmark)
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxHeight(),

                ) {
                // Rating (top right)
                Box(
                    modifier = Modifier
                        .size(37.dp, 16.dp)
                        .background(
                            AppColors.secondary20,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(vertical = 2.dp, horizontal = 7.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_star_6),
                            contentDescription = "average rating",
                            tint = AppColors.secondary100,
                            modifier = Modifier.size(8.dp),
                        )
                        Spacer(Modifier.width(2.dp))

                        Text(
                            text = rating.toString(),
                            style = AppTextStyles.smallerTextRegular.copy(fontSize = 8.sp),
                            modifier = Modifier.size(12.dp),
                        )

                    }
                }

                Spacer(Modifier.height(8.dp))

                // Time + Bookmark (bottom right)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.ic_timer_outline),
                        contentDescription = "timer",
                        tint = AppColors.white,
                        modifier = Modifier.size(17.dp)
                    )
                    Spacer(Modifier.width(5.dp))
                    Text(
                        text = time,
                        style = AppTextStyles.smallerTextRegular.copy(color = AppColors.white)
                    )
                    Spacer(Modifier.width(10.dp))
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(
                                AppColors.white,
                                shape = RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_bookmark_outline),
                            contentDescription = "book mark",
                            tint = AppColors.primary80,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun RecipeCardPreview() {
    RecipeCard(
        name = "Traditional spare ribs baked",
        imageUrl = "https://cdn.pixabay.com/photo/2017/11/10/15/04/steak-2936531_1280.jpg",
        chef = "Chef John",
        time = "20 min",
        rating = 4.0
    )
}
