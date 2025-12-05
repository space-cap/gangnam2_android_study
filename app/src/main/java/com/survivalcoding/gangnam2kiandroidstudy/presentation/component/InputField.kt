package com.survivalcoding.gangnam2kiandroidstudy.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppTextStyles

@Composable
fun InputField(
    label: String = "",
    placeholder: String = "",
    value: String = "",
    onValueChange: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    isPassword: Boolean = false
) {
    var passwordVisible by remember { mutableStateOf(false) }

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
            textStyle = AppTextStyles.smallTextRegular3,
            visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = if (isPassword) KeyboardOptions(keyboardType = KeyboardType.Password) else KeyboardOptions.Default,
            trailingIcon = if (isPassword) {
                {
                    val image = if (passwordVisible) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    }

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = image,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password"
                        )
                    }
                }
            } else {
                null
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InputFieldPreview() {
    var text by remember { mutableStateOf("example@email.com") }
    var password by remember { mutableStateOf("password") }

    Column {
        InputField(
            label = "Email",
            placeholder = "Enter your email",
            value = text,
            onValueChange = { text = it }
        )
        Spacer(modifier = Modifier.height(16.dp))
        InputField(
            label = "Password",
            placeholder = "Enter your password",
            value = password,
            onValueChange = { password = it },
            isPassword = true
        )
    }
}
