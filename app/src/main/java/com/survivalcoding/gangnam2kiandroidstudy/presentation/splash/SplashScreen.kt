package com.survivalcoding.gangnam2kiandroidstudy.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.survivalcoding.gangnam2kiandroidstudy.R
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.MediumButton
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppTextStyles


@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    uiState: SplashUiState = SplashUiState(),
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        Image(
            painter = painterResource(R.drawable.bg_main),
            contentDescription = "배경",
            contentScale = ContentScale.Crop, // 이미지가 비율을 유지하면서 빈 공간 없이 채워짐
            modifier = Modifier.fillMaxSize()
        )

        // 이미지 위에 겹칠 그라데이션 Box 추가
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent, // 위쪽은 투명
                            Color.Black        // 아래쪽은 검은색
                        ),
                        startY = 500f // 그라데이션 시작 위치 (중간부터 시작하도록 조절)
                    )
                )
        )

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_chef),
                contentDescription = "모자이미지",
                tint = AppColors.white,
                modifier = Modifier.size(79.dp)
            )

            Text(
                text = stringResource(R.string.splash_description),
                style = AppTextStyles.mediumTextBold.copy(color = AppColors.white),
                modifier = Modifier.padding(top = 14.dp)
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 50.dp, start = 40.dp, end = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.splash_title),
                    style = AppTextStyles.titleTextBold.copy(
                        color = AppColors.white,
                        fontSize = 50.sp,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 60.sp
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.splash_subtitle),
                    style = AppTextStyles.normalTextRegular.copy(color = AppColors.white),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(64.dp)) // ⬅️ 정확한 버튼 위 간격

            MediumButton(
                text = stringResource(R.string.splash_button),
                enabled = uiState.isNetworkAvailable,
                onClick = onClick,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    //SplashScreen(
    //    viewModel = SplashViewModel(MockNetworkChecker(true))
    //)
}
