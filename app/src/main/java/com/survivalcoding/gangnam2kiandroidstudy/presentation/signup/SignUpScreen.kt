package com.survivalcoding.gangnam2kiandroidstudy.presentation.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.survivalcoding.gangnam2kiandroidstudy.R
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.BigButton
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.InputField
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.LeeCheckBox
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.LeeIconBox
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.LeeLine
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppTextStyles

@Composable
fun SignUpScreen(
    onSignUpClick: () -> Unit = {},
) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isTermsAccepted by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 30.dp),
    ) {

        Spacer(modifier = Modifier.height(54.dp))

        Column() {
            Text(
                "Create an account",
                style = AppTextStyles.largeTextBold,
                color = AppColors.black,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                "Let's help you set up your account, it won't take long.",
                style = AppTextStyles.smallerTextRegular,
                color = AppColors.label1,
                modifier = Modifier.width(195.dp),
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        InputField(
            label = "Name",
            placeholder = "Enter name",
            value = name,
            onValueChange = { name = it },
        )

        Spacer(modifier = Modifier.height(10.dp))

        InputField(
            label = "Email",
            placeholder = "Enter email",
            value = email,
            onValueChange = { email = it },
        )

        Spacer(modifier = Modifier.height(10.dp))

        InputField(
            label = "Password",
            placeholder = "Enter password",
            value = password,
            onValueChange = { password = it },
            isPassword = true,
        )

        Spacer(modifier = Modifier.height(20.dp))

        InputField(
            label = "Confirm Password",
            placeholder = "Retype Password",
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            isPassword = true,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row() {
            LeeCheckBox(
                checked = isTermsAccepted,
                onCheckedChange = {
                    isTermsAccepted = it
                },
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                "Accept terms & Condition",
                style = AppTextStyles.smallerTextRegular,
                color = AppColors.secondary100,
            )
        }

        Spacer(modifier = Modifier.height(26.dp))

        BigButton(
            text = "Sign Up",
            onClick = onSignUpClick,
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            LeeLine()
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                "Or Sign in With",
                style = AppTextStyles.smallerTextRegular,
                color = AppColors.secondary100,
            )
            Spacer(modifier = Modifier.padding(4.dp))
            LeeLine()
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            LeeIconBox(icon = R.drawable.social_icons_google)
            Spacer(modifier = Modifier.padding(10.dp))
            LeeIconBox(icon = R.drawable.social_icons_facebook)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                "Already have an account?",
                style = AppTextStyles.smallerTextBold
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                "Sign In",
                style = AppTextStyles.smallerTextBold,
                color = AppColors.primary100,
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen()
}
