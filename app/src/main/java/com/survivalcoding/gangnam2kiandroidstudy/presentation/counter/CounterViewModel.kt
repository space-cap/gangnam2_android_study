package com.survivalcoding.gangnam2kiandroidstudy.presentation.counter

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class CounterViewModel : ViewModel() {
    // private: ViewModel 내부에서만 수정 가능한 MutableStateFlow
    // 외부에서 직접 수정하는 것을 방지
    private val _count = MutableStateFlow(0)

    // public: UI에서 관찰(observe)할 수 있는 읽기 전용 StateFlow
    // asStateFlow()로 변환하여 외부에서 값 변경 불가능하게 함
    val count = _count.asStateFlow()

    /**
     * 카운트를 1 증가시키는 함수
     */
    fun increment() {
        _count.value++
    }

    /**
     * 카운트를 1 감소시키는 함수
     */
    fun decrement() {
        _count.value--
    }
}
