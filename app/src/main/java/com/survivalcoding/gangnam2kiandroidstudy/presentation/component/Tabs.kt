package com.survivalcoding.gangnam2kiandroidstudy.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppTextStyles
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList


@Composable
fun Tabs(
    labels: ImmutableList<String>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val boxWidth = when(labels.size) {
        2 -> 150.dp
        3 -> 107.dp
        else -> 107.dp
    }

    val rowPadding = when(labels.size) {
        2 -> 30.dp
        3 -> 20.dp
        else -> 20.dp
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .size(375.dp, 58.dp)
            .padding(horizontal = rowPadding),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,

    ) {
        labels.take(3).forEachIndexed { index, label ->
            val isSelected = selectedIndex == index
            // val isSelected = true
            val backgroundColor = if (isSelected) AppColors.primary100 else Color.Transparent
            val contentColor = if (isSelected) AppColors.white else AppColors.primary100

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { onTabSelected(index) }
                    .background(backgroundColor)
                    .size(width = boxWidth, height = 33.dp),
                contentAlignment = Alignment.Center,

            ) {
                Text(
                    text = label,
                    color = contentColor,
                    style = AppTextStyles.smallerTextBold,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TabsPreview() {
    var selectedIndex by remember { mutableStateOf(0) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Tabs(
            labels = listOf("Label1", "Label2", "Label3", "Label4").toImmutableList(),
            selectedIndex = selectedIndex,
            onTabSelected = { selectedIndex = it }
        )
    }
}
