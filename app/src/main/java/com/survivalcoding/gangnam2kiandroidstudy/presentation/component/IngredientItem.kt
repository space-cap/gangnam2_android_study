package com.survivalcoding.gangnam2kiandroidstudy.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.survivalcoding.gangnam2kiandroidstudy.domain.model.Ingredient
import com.survivalcoding.gangnam2kiandroidstudy.domain.model.IngredientAmount
import com.survivalcoding.gangnam2kiandroidstudy.domain.model.UnitType
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppTextStyles

@Composable
fun IngredientItem(
    ingredient: Ingredient,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .background(AppColors.gray4)
            .padding(vertical = 15.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(AppColors.white, RoundedCornerShape(10.dp))
                .width(52.dp)
                .height(52.dp),
            contentAlignment = Alignment.Center,

        ) {
            AsyncImage(
                model = ingredient.image,
                contentDescription = ingredient.name,
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = ingredient.name,
            style = AppTextStyles.normalTextBold,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "${ingredient.amount.value} ${ingredient.amount.unit.label}",
            style = AppTextStyles.smallTextRegular,
            color = AppColors.gray3
        )
    }
}


@Preview(showBackground = true)
@Composable
fun IngredientItemPreview() {
    IngredientItem(
        ingredient = Ingredient(
            id = 1,
            image = "https://cdn.pixabay.com/photo/2017/10/06/17/17/tomato-2823826_1280.jpg",
            name = "Tomatos",
            amount = IngredientAmount(
                value = 1.0,
                unit = UnitType.PIECE
            )
        )
    )
}
