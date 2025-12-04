package com.survivalcoding.gangnam2kiandroidstudy.presentation.component

import android.R.attr.enabled
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.survivalcoding.gangnam2kiandroidstudy.R
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppTextStyles

@Composable
fun RatingDialog(
    title: String = "Rate recipe",
    actionName: String = "Send",
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit= {},
    rating: Int= 3,
) {
    Dialog(onDismissRequest = { onDismiss() }) {

        Card(
            modifier = Modifier
                .width(170.dp)
                .height(91.dp)
                .clip(RoundedCornerShape(8.dp))
                .padding(15.dp, 10.dp),
            shape = RoundedCornerShape(8.dp),

        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                Text(
                    text = title,
                    style = AppTextStyles.smallerTextRegular,
                    color = AppColors.label1
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    repeat(5) { index ->
                        val starIndex = index + 1
                        val isFilled = starIndex <= rating

//                        Text(
//                            text = "$starIndex / $isFilled",
//                            fontSize = 5.sp,
//                            color = AppColors.label1,
//                        )

                        Icon(
                            painter = painterResource(
                                if(isFilled) R.drawable.bold_star
                                else R.drawable.outline_star
                            ),
                            contentDescription = "star",
                            tint = AppColors.rating,
                            modifier = Modifier.size(20.dp, 20.dp)
                                .clickable { onConfirm() },
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                val enabled = rating > 0
                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .height(20.dp)
                        .clip(RoundedCornerShape(10))
                        .background(AppColors.primary100)
                        .clickable(
                            enabled = enabled,
                            onClick = onConfirm
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = actionName,
                        style = AppTextStyles.smallerTextSmallLabel,
                        color = AppColors.white
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
