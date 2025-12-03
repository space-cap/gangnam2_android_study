package com.survivalcoding.gangnam2kiandroidstudy.presentation.component

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test

class InputFieldTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `InputField_초기_값과_라벨이_표시된다`() {
        val label = "Email"
        val value = "test@example.com"

        composeTestRule.setContent {
            InputField(
                label = label,
                value = value,
                onValueChange = {}
            )
        }

        composeTestRule.onNodeWithText(label).assertIsDisplayed()
        composeTestRule.onNodeWithText(value).assertIsDisplayed()
    }

    @Test
    fun `InputField_값이_없을때_플레이스홀더가_표시된다`() {
        val label = "Email"
        val placeholder = "Enter your email"

        composeTestRule.setContent {
            InputField(
                label = label,
                placeholder = placeholder,
                value = "",
                onValueChange = {}
            )
        }

        composeTestRule.onNodeWithText(placeholder).assertIsDisplayed()
    }

    @Test
    fun `InputField_텍스트를_입력하면_onValueChange가_호출된다`() {
        val label = "Email"
        val typedText = "new text"

        composeTestRule.setContent {
            var value by remember { mutableStateOf("") }
            InputField(
                label = label,
                value = value,
                onValueChange = { value = it }
            )
        }

        // OutlinedTextField 는 label 을 직접 가지고 있지 않고, semantic tree 에서 별도의 노드로 존재하므로
        // label 을 가진 노드에 직접 text input 을 할 수 없습니다.
        // 대신 placeholder 나 초기 value 로 노드를 찾아야 합니다.
        // 여기서는 placeholder 가 없으므로, 초기 value 가 비어있는 노드를 찾습니다.
        composeTestRule.onNodeWithText("").performTextInput(typedText)
        composeTestRule.onNodeWithText(typedText).assertIsDisplayed()
    }
}
