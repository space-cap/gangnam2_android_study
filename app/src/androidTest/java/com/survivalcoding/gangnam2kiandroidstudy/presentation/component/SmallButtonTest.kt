package com.survivalcoding.gangnam2kiandroidstudy.presentation.component

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class SmallButtonTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `작은_버튼_클릭_테스트`() {

        // given
        var counter = 0

        // Start the app
        composeTestRule.setContent {
            SmallButton(
                "Button",
                onClick = {
                    counter = 1
                }
            )
        }

        // when
        composeTestRule.onNodeWithText("Button").performClick()

        // then
        assertEquals(1, counter)
    }
}