package com.survivalcoding.gangnam2kiandroidstudy.presentation.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.survivalcoding.gangnam2kiandroidstudy.R
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppTextStyles

@Composable
fun SmallButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
){
    Button(
        onClick = onClick,
        modifier = modifier
            .size(width = 174.dp, height = 37.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppColors.primary100,
            contentColor = AppColors.white
        )
    ) {
        Text(
            text = text,
            style = AppTextStyles.normalTextBold,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SmallButtonPreview() {
    SmallButton("Button")
}
