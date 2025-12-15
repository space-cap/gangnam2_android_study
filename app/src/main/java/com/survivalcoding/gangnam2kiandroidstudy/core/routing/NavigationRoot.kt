package com.survivalcoding.gangnam2kiandroidstudy.core.routing


import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.survivalcoding.gangnam2kiandroidstudy.presentation.home.HomeRoot
import com.survivalcoding.gangnam2kiandroidstudy.presentation.main.MainScreen
import com.survivalcoding.gangnam2kiandroidstudy.presentation.notifications.NotificationsScreen
import com.survivalcoding.gangnam2kiandroidstudy.presentation.profile.ProfileScreen
import com.survivalcoding.gangnam2kiandroidstudy.presentation.recipedetails.RecipeDetailsScreen
import com.survivalcoding.gangnam2kiandroidstudy.presentation.savedrecipes.SavedRecipesRoot
import com.survivalcoding.gangnam2kiandroidstudy.presentation.signin.SignInScreen
import com.survivalcoding.gangnam2kiandroidstudy.presentation.signup.SignUpScreen
import com.survivalcoding.gangnam2kiandroidstudy.presentation.splash.SplashScreen

/**
 * 앱의 전체적인 탐색(navigation) 구조를 정의하는 최상위 Composable 함수입니다.
 *
 * @param modifier 이 Composable에 적용할 Modifier.
 */
@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
) {
    // rememberNavBackStack은 탐색 스택을 생성하고 기억합니다.
    // 초기 화면으로 Route.Splash을 설정합니다.
    val topLevelBackStack = rememberNavBackStack(Route.Splash)

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
                SplashScreen(
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

            entry<Route.Home> {
                HomeRoot()
            }

            entry<Route.SavedRecipes> { SavedRecipesRoot() }

            entry<Route.RecipeDetail> { RecipeDetailsScreen(it.recipeId) }

            entry<Route.Main> {
                val backStack = rememberNavBackStack(Route.Home)

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
                                entry<Route.Home> { HomeRoot() }
                                entry<Route.SavedRecipes> { SavedRecipesRoot() }
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