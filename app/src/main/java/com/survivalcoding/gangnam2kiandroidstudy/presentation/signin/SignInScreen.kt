package com.survivalcoding.gangnam2kiandroidstudy.presentation.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.BigButton
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.InputField
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppTextStyles
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppTextStyles.headerTextRegular

@Composable
fun SignInScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 30.dp),


        ) {

        Spacer(modifier = Modifier.height(94.dp))

        Column() {
            Text(
                "Hello,",
                style = AppTextStyles.headerTextRegular,
                color = AppColors.black,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                "Welcome Back!",
                style = AppTextStyles.largeTextRegular,
                color = AppColors.label1,
            )
        }

        Spacer(modifier = Modifier.height(83.dp))

        InputField(
            label = "Email",
            placeholder = "Enter your email",
        )

        Spacer(modifier = Modifier.height(30.dp))

        InputField(
            label = "Password",
            placeholder = "Enter your password",
            isPassword = true,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "Forgot Password?",
            style = AppTextStyles.smallTextRegular,
            color = AppColors.primary100
        )

        Spacer(modifier = Modifier.height(25.dp))

        BigButton(
            text = "Sign In",
        )

    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    SignInScreen()
}
