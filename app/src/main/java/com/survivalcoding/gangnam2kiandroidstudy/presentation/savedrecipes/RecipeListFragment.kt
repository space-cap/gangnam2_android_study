package com.survivalcoding.gangnam2kiandroidstudy.presentation.savedrecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.Text
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment

class RecipeListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // XML 대신 ComposeView를 반환하여 Fragment 내부를 Compose로 그립니다.
        return ComposeView(requireContext()).apply {
            setContent {
                // 여기에 실제 리스트 UI (RecipeListScreen 등)를 넣으세요
                Text(text = "여기가 저장된 레시피 리스트가 보일 곳입니다.")
            }
        }
    }
}
