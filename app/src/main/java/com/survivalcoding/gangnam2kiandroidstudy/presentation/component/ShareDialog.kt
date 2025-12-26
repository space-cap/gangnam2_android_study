package com.survivalcoding.gangnam2kiandroidstudy.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppTextStyles


@Composable
fun ShareDialog(
    shareUrl: String,
    isVisible: Boolean = false,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    onCopy: (String) -> Unit = {},
) {
    if (!isVisible) {
        return
    }

    val clipboardManager = LocalClipboardManager.current

    // 코루틴 스코프를 생성합니다.
    val scope = rememberCoroutineScope()

    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Box(
            modifier = modifier.width(310.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = AppColors.white,
                        shape = RoundedCornerShape(20.dp),
                    )
                    .padding(horizontal = 15.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Text(
                    text = "Recipe Link",
                    style = AppTextStyles.largeTextBold,
                )
                Text(
                    text = "Copy recipe link and share your recipe link with friends and family.",
                    style = AppTextStyles.smallTextRegular,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = AppColors.gray4,
                            shape = RoundedCornerShape(9.dp),
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = shareUrl,
                        style = AppTextStyles.smallTextRegular.copy(
                            fontWeight = FontWeight.SemiBold,
                        ),
                        modifier = Modifier.padding(start = 14.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    CopyLinkButton(
                        text = "Copy Link",
                        modifier = Modifier.width(85.dp),
                    ) {
                        // 코루틴 없이 직접 호출합니다.
                        clipboardManager.setText(AnnotatedString(shareUrl))

                        onCopy(shareUrl)
                        onDismissRequest()
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, end = 15.dp),
                contentAlignment = Alignment.TopEnd,
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "close",
                    modifier = Modifier
                        .size(5.dp)
                        .clickable { onDismissRequest() },
                )
            }
        }
    }
}

@Composable
fun CopyLinkButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val shape = RoundedCornerShape(9.dp)
    val backgroundColor = if (isPressed || !enabled) AppColors.gray4 else AppColors.primary100

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(43.dp)
            .clip(shape)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = onClick,
            )
            .background(color = backgroundColor, shape = shape),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = AppTextStyles.smallerTextBold.copy(color = AppColors.white),
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 12.dp),
        )
    }
}

@Preview
@Composable
private fun ShareDialogPreview() {
    ShareDialog(
        isVisible = true,
        shareUrl = "app.Recipe.co/jollof_rice",
    )
}