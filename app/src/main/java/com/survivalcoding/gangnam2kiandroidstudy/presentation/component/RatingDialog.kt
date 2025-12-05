package com.survivalcoding.gangnam2kiandroidstudy.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.survivalcoding.gangnam2kiandroidstudy.R
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppTextStyles

@Composable
fun RatingDialog(
    title: String = "Rate recipe",
    actionName: String = "Send",
    onDismiss: () -> Unit = {},
    onConfirm: (Int) -> Unit= {},
    initialRating: Int = 3,
) {
    var rating by remember { mutableIntStateOf(initialRating) }

    Dialog(onDismissRequest = { onDismiss() }) {

        Card(
            modifier = Modifier
                .width(170.dp)
                .height(91.dp)
                .background(
                    color = AppColors.white,
                    shape = RoundedCornerShape(8.dp)
                ),
            shape = RoundedCornerShape(8.dp),

        ) {
            Column(
                modifier = Modifier.padding(
                    vertical = 10.dp,
                    horizontal = 15.dp,
                ).fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                Text(
                    text = title,
                    style = AppTextStyles.smallerTextRegular,
                    color = AppColors.label1,
                    modifier = Modifier
                        .height(17.dp)
                )

                Row(
                    modifier = Modifier
                        .padding(
                            vertical = 5.dp,
                        )
                        .height(24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    repeat(5) { index ->
                        val starIndex = index + 1
                        val isFilled = starIndex <= rating

                        Icon(
                            painter = painterResource(
                                if(isFilled) R.drawable.bold_star
                                else R.drawable.outline_star
                            ),
                            contentDescription = "star",
                            modifier = Modifier
                                .size(18.dp)
                                .clickable {
                                    rating = starIndex
                                },
                            tint = AppColors.rating,
                        )
                        if (index < 4) {
                            Spacer(Modifier.width(10.dp))
                        }
                    }
                }

                val enabled = rating > 0
                Box(
                    modifier = Modifier
                        .height(20.dp)
                        .background(
                            color = if (rating != 0) AppColors.rating else AppColors.gray4,
                            shape = RoundedCornerShape(6.dp)
                        )
                        .clickable {
                            if (rating != 0) {
                                onConfirm(rating)
                                onDismiss()
                            }
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = actionName,
                        style = AppTextStyles.smallerTextSmallLabel,
                        color = AppColors.white,
                        modifier = Modifier
                            .padding(
                                vertical = 4.dp,
                                horizontal = 20.dp
                            )
                    )
                }

            }
        }
    }
}


@Preview
@Composable
fun RatingDialogPreview() {
    RatingDialog()
}
