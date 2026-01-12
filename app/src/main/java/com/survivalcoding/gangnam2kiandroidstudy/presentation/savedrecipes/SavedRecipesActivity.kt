package com.survivalcoding.gangnam2kiandroidstudy.presentation.savedrecipes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.gangnam2kiandroidstudy.R

class SavedRecipesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_recipes)

        // 화면이 처음 생성될 때만 Fragment를 추가합니다 (회전 시 중복 방지)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, RecipeListFragment())
                .commit()
        }
    }
}
