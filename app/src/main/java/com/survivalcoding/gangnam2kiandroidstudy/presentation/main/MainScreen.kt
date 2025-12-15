package com.survivalcoding.gangnam2kiandroidstudy.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.survivalcoding.gangnam2kiandroidstudy.R
import com.survivalcoding.gangnam2kiandroidstudy.core.routing.Route


@Composable
fun MainScreen(
    body: @Composable (modifier: Modifier) -> Unit,
    backStack: NavBackStack<NavKey>,
    modifier: Modifier = Modifier,
) {
    val currentRoute = backStack.lastOrNull()

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentRoute is Route.Home,
                    onClick = {
                        if (currentRoute !is Route.Home) {
                            backStack.clear()
                            backStack.add(Route.Home)
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_home_1),
                            contentDescription = "Home",
                        )
                    },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = currentRoute is Route.SavedRecipes,
                    onClick = {
                        if (currentRoute !is Route.SavedRecipes) {
                            backStack.clear()
                            backStack.add(Route.SavedRecipes)
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.inactive),
                            contentDescription = "Saved Recipes"
                        )
                    },
                    label = { Text("Saved") }
                )
                NavigationBarItem(
                    selected = currentRoute is Route.Notifications,
                    onClick = {
                        if (currentRoute !is Route.Notifications) {
                            backStack.clear()
                            backStack.add(Route.Notifications)
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_notification_bing_1),
                            contentDescription = "Notifications"
                        )
                    },
                    label = { Text("Notifications") }
                )
                NavigationBarItem(
                    selected = currentRoute is Route.Profile,
                    onClick = {
                        if (currentRoute !is Route.Profile) {
                            backStack.clear()
                            backStack.add(Route.Profile)
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_profile_outline),
                            contentDescription = "Profile"
                        )
                    },
                    label = { Text("Profile") }
                )
            }
        }
    ) { innerPadding ->
        body(modifier.padding(innerPadding))
    }
}
