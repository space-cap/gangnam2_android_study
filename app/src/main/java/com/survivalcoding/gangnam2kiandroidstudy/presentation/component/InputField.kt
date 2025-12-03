package com.survivalcoding.gangnam2kiandroidstudy.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppTextStyles

@Composable
fun InputField(
    label: String = "",
    placeholder: String = "",
    value: String = "",
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .size(315.dp, 81.dp)

    ) {
        Text(
            text = label,
            color = AppColors.gray1,
            style = AppTextStyles.smallTextRegular,
            modifier = Modifier.size(38.dp, 21.dp)
        )
        Spacer(modifier = Modifier.height(7.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier.size(315.dp, 55.dp),
            placeholder = {
                Text(
                    placeholder,
                    style = AppTextStyles.smallTextRegular2,
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AppColors.primary100,
                unfocusedBorderColor = AppColors.gray4
            ),
            textStyle = AppTextStyles.smallTextRegular3
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InputFieldPreview() {
    InputField(
        label = "Email",
        placeholder = "Enter your email",
        value = "example@email.com",
        onValueChange = {}
    )
}