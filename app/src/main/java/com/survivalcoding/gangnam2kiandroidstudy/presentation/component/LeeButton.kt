package com.survivalcoding.gangnam2kiandroidstudy.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppColors

@Composable
fun LeeButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Box(
        modifier = modifier
            .clickable(
                enabled = enabled
            ) { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "버튼",
            color = AppColors.white,
        )

    }
}

@Preview(showBackground = true)
@Composable
fun LeeButtonPreview() {
    LeeButton(onClick = {})
}


