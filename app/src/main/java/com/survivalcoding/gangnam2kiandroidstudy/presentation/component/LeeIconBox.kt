package com.survivalcoding.gangnam2kiandroidstudy.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.survivalcoding.gangnam2kiandroidstudy.R
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppColors

@Composable
fun LeeIconBox(
    icon: Int = R.drawable.social_icons_google,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(44.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(10.dp),
                ambientColor = Color(0xFF696969),
                spotColor = Color(0xFF696969),
            )
            .background(
                color = AppColors.white,
                shape = RoundedCornerShape(10.dp),
            )
            .clickable {}
            .padding(10.dp),
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "social login",
            modifier = Modifier.size(24.dp, 24.dp),
            tint = Color.Unspecified,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LeeIconBoxPreview() {
    LeeIconBox()
}
