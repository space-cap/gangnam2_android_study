package com.survivalcoding.gangnam2kiandroidstudy.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.survivalcoding.gangnam2kiandroidstudy.R
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppTextStyles

@Composable
fun RatingButton(
    text: String = "4",
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {
    val borderColor = AppColors.primary100
    val background = if (isSelected) borderColor else Color.Transparent
    val contentColor = if (isSelected) Color.White else borderColor
    val starColor = if (isSelected) AppColors.white else AppColors.primary80



    Box(
        modifier = Modifier
            .width(50.dp)
            .height(28.dp)
            .clip(RoundedCornerShape(5.dp)) // pill 모양[web:28][web:31]
            .background(background)
            .border(
                width = if (isSelected) 0.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(5.dp)
            )
            .clickable(onClick = onClick), // 클릭 처리[web:30][web:32]
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = text,
                color = contentColor,
                style = AppTextStyles.smallerTextRegular
            )
            Spacer(modifier = Modifier.width(5.dp))
            Icon(
                painter = painterResource(R.drawable.outline_star),
                contentDescription = "star",
                tint = starColor,
                modifier = Modifier.size(18.dp, 18.dp),
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RatingButtonPreview() {
    Row() {
        RatingButton("5", false)
        Spacer(modifier = Modifier.width(10.dp))
        RatingButton("5", true)
    }
}
