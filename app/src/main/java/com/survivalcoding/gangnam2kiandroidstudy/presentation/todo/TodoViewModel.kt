package com.survivalcoding.gangnam2kiandroidstudy.presentation.todo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.survivalcoding.gangnam2kiandroidstudy.AppApplication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Todo 목록 화면의 UI 상태를 나타내는 데이터 클래스
 *
 * @property todos 할 일 목록
 * @property isLoading 데이터 로딩 상태
 */
data class TodoUiState(
    val todos: List<Todo> = emptyList(),
    val isLoading: Boolean = false,
)


/**
 * Todo 화면의 ViewModel
 *
 * @param todoRepository Todo 데이터를 관리하는 Repository (의존성 주입)
 * @param savedStateHandle 프로세스 종료 시에도 상태를 보존하는 핸들
 *                         (예: 백그라운드에서 앱이 종료되었다가 복원될 때)
 */
class TodoViewModel(
    private val todoRepository: TodoRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    // UI 상태를 관리하는 MutableStateFlow (private)
    private val _uiState = MutableStateFlow(TodoUiState())

    // 외부로 노출되는 읽기 전용 StateFlow
    val uiState = _uiState.asStateFlow()

    /**
     * ViewModel이 생성될 때 초기 데이터를 로드
     */
    init {
        viewModelScope.launch {
            // 1. 데이터 로딩 시작 -> isLoading을 true로 변경
            _uiState.value = _uiState.value.copy(isLoading = true)

            // 2. Repository에서 할 일 목록을 가져옴 (시간이 걸릴 수 있는 작업)
            val todos = todoRepository.getTodos()

            // 3. 데이터 로딩 완료 -> 받아온 데이터(todos)와 함께 isLoading을 false로 변경
            _uiState.value = _uiState.value.copy(todos = todos, isLoading = false)
        }
    }

    fun addTodo(todo: Todo) {
        viewModelScope.launch {
            todoRepository.addTodo(todo)
            val todos = todoRepository.getTodos()
            //_uiState.value = _uiState.value.copy(todos = todos)
            _uiState.update {
                it.copy(todos = todos)
            }
        }
    }


    companion object {

        /**
         * ViewModel 생성을 위한 Factory
         *
         * ViewModel은 기본 생성자만 지원하므로, 파라미터가 있는 경우
         * Factory를 통해 의존성을 주입해야 함
         *
         * 사용법:
         * val viewModel: TodoViewModel by viewModels { TodoViewModel.Factory }
         */
        val Factory: ViewModelProvider.Factory = viewModelFactory {

            // initializer: ViewModel 인스턴스를 생성하는 람다
            initializer {

                // SavedStateHandle 생성
                // 화면 회전, 프로세스 종료 등의 상황에서 상태 복원에 사용
                val savedStateHandle = createSavedStateHandle()

                // Application 인스턴스에서 Repository 가져오기
                // this[APPLICATION_KEY]: ViewModelProvider.CreationExtras에서 Application 객체 접근
                // AppApplication으로 캐스팅하여 todoRepository 프로퍼티 접근
                val todoRepository = (this[APPLICATION_KEY] as AppApplication).todoRepository

                // 의존성을 주입하여 TodoViewModel 인스턴스 생성 및 반환
                TodoViewModel(
                    todoRepository,
                    savedStateHandle = savedStateHandle,
                )
            }
        }
    }
}
