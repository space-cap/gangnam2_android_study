package com.survivalcoding.gangnam2kiandroidstudy.core.routing


import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.survivalcoding.gangnam2kiandroidstudy.presentation.home.HomeRoot
import com.survivalcoding.gangnam2kiandroidstudy.presentation.main.MainScreen
import com.survivalcoding.gangnam2kiandroidstudy.presentation.notifications.NotificationsScreen
import com.survivalcoding.gangnam2kiandroidstudy.presentation.profile.ProfileScreen
import com.survivalcoding.gangnam2kiandroidstudy.presentation.recipedetails.RecipeDetailsRoot
import com.survivalcoding.gangnam2kiandroidstudy.presentation.savedrecipes.SavedRecipesRoot
import com.survivalcoding.gangnam2kiandroidstudy.presentation.searchrecipes.SearchRecipesRoot
import com.survivalcoding.gangnam2kiandroidstudy.presentation.signin.SignInScreen
import com.survivalcoding.gangnam2kiandroidstudy.presentation.signup.SignUpScreen
import com.survivalcoding.gangnam2kiandroidstudy.presentation.splash.SplashRoot


/**
 * 앱의 전체적인 탐색(navigation) 구조를 정의하는 최상위 Composable 함수입니다.
 *
 * @param modifier 이 Composable에 적용할 Modifier.
 */
@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    deepLinkUri: String? = null,
) {
    // rememberNavBackStack은 탐색 스택을 생성하고 기억합니다.
    // 초기 화면으로 Route.Splash을 설정합니다.
    val topLevelBackStack = rememberNavBackStack(Route.Splash)
    val backStack = rememberNavBackStack(Route.Home)


    LaunchedEffect(key1 = deepLinkUri) {
        if (deepLinkUri != null) {
            val uri = deepLinkUri.toUri()
            if (uri.scheme == "app" && uri.host == "recipe.co") {
                val recipeId = uri.lastPathSegment?.toIntOrNull()

                if (recipeId != null) {
                    topLevelBackStack.clear()
                    backStack.clear()

                    topLevelBackStack.add(Route.Main)
                    backStack.add(Route.SavedRecipes)
                    topLevelBackStack.add(Route.RecipeDetails(recipeId.toLong()))
                }
            }
        }
    }


    // NavDisplay는 현재 탐색 스택의 최상위 entry에 해당하는 화면을 표시합니다.
    NavDisplay(
        modifier = modifier,

        // entryDecorators는 각 화면(entry)이 표시될 때 추가적인 기능을 제공합니다.
        // - rememberSaveableStateHolderNavEntryDecorator: 화면 전환 시 상태를 저장하고 복원합니다.
        // - rememberViewModelStoreNavEntryDecorator: 화면마다 별도의 ViewModel을 유지할 수 있도록 합니다.
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        backStack = topLevelBackStack,

        // entryProvider는 각 라우트(경로)에 어떤 화면을 보여줄지 정의합니다.
        entryProvider = entryProvider {
            entry<Route.Splash> {
                SplashRoot(
                    onClick = {
                        Log.d("NavigationRoot", "Splash -> SignIn")
                        topLevelBackStack.clear()
                        topLevelBackStack.add(Route.SignIn)
                    }
                )
            }
            entry<Route.SignIn> {
                SignInScreen(
                    onLogin = {
                        Log.d("NavigationRoot", "SignIn -> Main")
                        topLevelBackStack.clear()
                        topLevelBackStack.add(Route.Main)
                    },
                    onSignUp = {
                        Log.d("NavigationRoot", "SignIn -> SignUp")
                        topLevelBackStack.clear()
                        topLevelBackStack.add(Route.SignUp)
                    }
                )
            }
            entry<Route.SignUp> {
                SignUpScreen(
                    onSignUpClick = {
                        Log.d("NavigationRoot", "SignUp -> SignIn or Main")
                        topLevelBackStack.clear()
                        topLevelBackStack.add(Route.SignIn) // 또는 Route.Main
                    },
                    onSignInClick = {
                        Log.d("NavigationRoot", "SignUp -> SignIn")
                        topLevelBackStack.clear()
                        topLevelBackStack.add(Route.SignIn)
                    }
                )
            }

            entry<Route.SearchRecipes> {
                SearchRecipesRoot(
                    onBackClick = {
                        Log.d("NavigationRoot", "SearchRecipes -> Main")
                        topLevelBackStack.remove(it)
                    }
                )
            }

            entry<Route.RecipeDetails> {
                RecipeDetailsRoot(
                    id = it.recipeId,
                    onBackClick = { topLevelBackStack.remove(it) },
                )
            }

            entry<Route.Main> {


                MainScreen(
                    backStack = backStack,
                    body = {
                        NavDisplay(
                            modifier = modifier,
                            backStack = backStack,
                            entryDecorators = listOf(
                                rememberSaveableStateHolderNavEntryDecorator(),
                                rememberViewModelStoreNavEntryDecorator()
                            ),
                            entryProvider = entryProvider {
                                entry<Route.Home> {
                                    HomeRoot(
                                        onSearchClick = {
                                            Log.d("NavigationRoot", "Main -> SearchRecipes")
                                            topLevelBackStack.add(Route.SearchRecipes)
                                        },
                                        onRecipeClick = { recipeId ->
                                            Log.d("NavigationRoot", "Main -> RecipeDetails")
                                            topLevelBackStack.add(Route.RecipeDetails(recipeId))
                                        }
                                    )
                                }
                                entry<Route.SavedRecipes> {
                                    SavedRecipesRoot(
                                        onItemClick = { recipeId ->
                                            topLevelBackStack.removeIf { it is Route.RecipeDetails }
                                            topLevelBackStack.add(Route.RecipeDetails(recipeId))
                                        },
                                    )
                                }
                                entry<Route.Notifications> { NotificationsScreen() }
                                entry<Route.Profile> { ProfileScreen() }
                            }
                        )
                    }
                )
            }
        }
    )
}