package com.survivalcoding.gangnam2kiandroidstudy.presentation.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.survivalcoding.gangnam2kiandroidstudy.R
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.BigButton
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.InputField
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.LeeIconBox
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppTextStyles

@Composable
fun SignInScreen() {
    // 비밀번호 필드로 포커스를 이동시키기 위한 FocusRequester 생성
    val passwordFocusRequester = remember { FocusRequester() }
    // 키보드 컨트롤러
    val keyboardController = LocalSoftwareKeyboardController.current

    // 이메일과 비밀번호 입력을 저장하기 위한 상태 변수
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
            value = email,
            onValueChange = { email = it },
            // 키보드 액션을 '다음'으로 설정
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            // '다음' 버튼 클릭 시 비밀번호 필드로 포커스 이동
            keyboardActions = KeyboardActions(onNext = { passwordFocusRequester.requestFocus() })
        )

        Spacer(modifier = Modifier.height(30.dp))

        InputField(
            label = "Password",
            placeholder = "Enter your password",
            value = password,
            onValueChange = { password = it },
            isPassword = true,
            // FocusRequester를 비밀번호 필드에 연결
            modifier = Modifier.focusRequester(passwordFocusRequester),
            // 키보드 액션을 '완료'로 설정
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            // '완료' 버튼 클릭 시 키보드 숨김
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
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

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            LeeIconBox(icon = R.drawable.social_icons_google)
            Spacer(modifier = Modifier.width(10.dp))
            LeeIconBox(icon = R.drawable.social_icons_facebook)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                "Don't have an account?",
                style = AppTextStyles.smallerTextRegular
            )
            Spacer(modifier = Modifier.padding(4.dp))

            Text(
                "Sign Up",
                style = AppTextStyles.smallerTextBold,
                color = AppColors.primary100,
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    SignInScreen()
}
