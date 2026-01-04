package com.survivalcoding.gangnam2kiandroidstudy.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.survivalcoding.gangnam2kiandroidstudy.utils.NetworkChecker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


/**
 * 스플래시 화면에서 초기 작업(네트워크 체크, 토큰 체크 등)을 처리하는 ViewModel.
 *
 * @param networkChecker 네트워크 상태를 확인하는 인터페이스
 */
class SplashViewModel(
    private val networkChecker: NetworkChecker
) : ViewModel() {
    // 내부에서만 수정 가능한 MutableStateFlow
    private val _uiState = MutableStateFlow(SplashUiState())

    // 외부(UI)에서는 읽기만 가능한 StateFlow로 노출
    val uiState = _uiState.asStateFlow()

    init {
        // ViewModel 생성 시 스플래시 초기 로직 실행
        checkInitialState()
        retrieveAndLogFcmToken()
    }


    private fun retrieveAndLogFcmToken() {

    }


    /**
     * 스플래시에서 실행할 초기 로직.
     * - 네트워크 상태 확인
     * - (추가로) 로그인 토큰 체크, 버전 체크 등도 여기에 포함 가능
     */
    private fun checkInitialState() {
        viewModelScope.launch {
            // 1. 스플래시 로딩 시작
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            // 2. 네트워크 상태 확인
            val isNetworkOk = networkChecker.isInternetAvailable()

            if (!isNetworkOk) {
                // 네트워크 없을 때 상태 업데이트
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isNetworkAvailable = false,
                    errorMessage = "인터넷 연결을 확인해주세요."
                )
                // 여기서는 바로 네비게이션하지 않고, 사용자가 재시도 버튼을 누르게 할 수도 있음
                return@launch
            }

            _uiState.value = _uiState.value.copy(
                isLoading = false,
                isNetworkAvailable = true,
            )
        }
    }
}