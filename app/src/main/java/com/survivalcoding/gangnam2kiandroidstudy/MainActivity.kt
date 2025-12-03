package com.survivalcoding.gangnam2kiandroidstudy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.BigButton
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.InputField
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.MediumButton
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.SmallButton
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.Tabs
import kotlinx.collections.immutable.toImmutableList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Column {
                    BigButton(
                        text = "Button",
                        onClick = {
                            println("클릭!")
                        }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    MediumButton(
                        text = "Button",
                        onClick = {
                            println("클릭!")
                        }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    SmallButton(
                        text = "Button",
                        onClick = {
                            println("클릭!")
                        }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    val focusRequester = remember { FocusRequester() }
                    var email by remember { mutableStateOf("") }

                    LaunchedEffect(Unit) {
                        focusRequester.requestFocus()
                    }

                    InputField(
                        label = "Email",
                        placeholder = "Enter your email",
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier.focusRequester(focusRequester)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    InputField(
                        label = "Email",
                        placeholder = "Enter your email",
                        value = email,
                        onValueChange = { email = it }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    var selectedIndex by remember { mutableStateOf(0) }
                    Tabs(
                        labels = listOf("Label1", "Label2", "Label3").toImmutableList(),
                        selectedIndex = selectedIndex,
                        onTabSelected = { selectedIndex = it }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Tabs(
                        labels = listOf("Label1", "Label2", "Label3").toImmutableList(),
                        selectedIndex = 1,
                        onTabSelected = { selectedIndex = it }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Tabs(
                        labels = listOf("Label1", "Label2", "Label3").toImmutableList(),
                        selectedIndex = 2,
                        onTabSelected = { selectedIndex = it }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Tabs(
                        labels = listOf("Label1", "Label2").toImmutableList(),
                        selectedIndex = 0,
                        onTabSelected = { selectedIndex = it }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Tabs(
                        labels = listOf("Label1", "Label2").toImmutableList(),
                        selectedIndex = 1,
                        onTabSelected = { selectedIndex = it }
                    )

                }

            }

        }
    }
}
