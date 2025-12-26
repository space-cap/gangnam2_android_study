package com.survivalcoding.gangnam2kiandroidstudy.presentation.home

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.survivalcoding.gangnam2kiandroidstudy.domain.model.Recipe
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun onBookmarkClick_isCalled_whenBookmarkIconIsClicked() {
        // Given: A recipe to be displayed and a variable to capture the action
        val testRecipe = Recipe(
            id = 123,
            name = "Test Recipe",
            image = "",
            chef = "Test Chef",
            time = "10 min",
            rating = 5.0,
            category = "Test"
        )
        val homeState = HomeState(
            filteredRecipes = listOf(testRecipe)
        )
        var capturedAction: HomeAction? = null

        // When: HomeScreen is displayed and the bookmark icon is clicked
        composeTestRule.setContent {
            HomeScreen(
                state = homeState,
                onAction = { action ->
                    capturedAction = action
                }
            )
        }

        // The Icon's contentDescription is "북마크 아이콘"
        // Find the node by its content description and perform a click.
        composeTestRule.onNodeWithContentDescription("북마크 아이콘").performClick()

        // Then: The correct action should be captured
        assertNotNull("Action was not captured", capturedAction)
        assertTrue("Captured action is not OnBookmarkClick", capturedAction is HomeAction.OnBookmarkClick)

        val bookmarkAction = capturedAction as HomeAction.OnBookmarkClick
        assertEquals("The recipe in the action is not the correct one", testRecipe, bookmarkAction.recipe)
    }
}
