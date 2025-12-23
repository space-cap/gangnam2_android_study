package com.survivalcoding.gangnam2kiandroidstudy.presentation.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.survivalcoding.gangnam2kiandroidstudy.R
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppTextStyles

@Composable
fun MediumButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState() // 누르는 중인지 감지

    Button(
        onClick = onClick,
        modifier = modifier
            .size(width = 243.dp, height = 54.dp),
        shape = RoundedCornerShape(10.dp),
        interactionSource = interactionSource,
        enabled = !isPressed,
        colors = ButtonDefaults.buttonColors(
            containerColor = AppColors.primary100,
            contentColor = AppColors.white
        )
    ) {
        Text(
            text = text,
            style = AppTextStyles.normalTextBold,
        )
        Spacer(modifier = Modifier.width(11.dp))
        Icon(
            painter = painterResource(R.drawable.arrow_forward_24px),
            contentDescription = "오른쪽 화살표"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MediumButtonPreview() {
    MediumButton("Button")
}
