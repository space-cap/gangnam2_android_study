package com.survivalcoding.gangnam2kiandroidstudy.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.survivalcoding.gangnam2kiandroidstudy.R
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppColors

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp)
            .background(Color.White)
            .padding(top = 5.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Top
    ) {
        IconButton(onClick = { /* TODO: Handle navigation */ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_home_1),
                contentDescription = "Home",
                modifier = Modifier.size(24.dp),
                tint = AppColors.gray4
            )
        }
        IconButton(onClick = { /* TODO: Handle navigation */ }) {
            Icon(
                painter = painterResource(id = R.drawable.inactive),
                contentDescription = "Saved",
                modifier = Modifier.size(24.dp),
                tint = AppColors.gray4
            )
        }
        IconButton(onClick = { /* TODO: Handle navigation */ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_notification_bing_1),
                contentDescription = "Notifications",
                modifier = Modifier.size(24.dp),
                tint = AppColors.gray4
            )
        }
        IconButton(onClick = { /* TODO: Handle navigation */ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_profile_outline),
                contentDescription = "Profile",
                modifier = Modifier.size(24.dp),
                tint = AppColors.gray4
            )
        }
    }
}

@Preview
@Composable
fun BottomNavigationBarPreview() {
    BottomNavigationBar()
}
