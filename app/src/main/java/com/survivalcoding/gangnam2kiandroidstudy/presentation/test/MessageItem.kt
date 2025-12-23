package com.survivalcoding.gangnam2kiandroidstudy.presentation.test

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MessageItem(
    message: Message
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ){
        Text(text = message.text)
    }
}
