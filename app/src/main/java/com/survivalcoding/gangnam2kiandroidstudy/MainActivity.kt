package com.survivalcoding.gangnam2kiandroidstudy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

                    InputField(
                        label = "Email",
                        placeholder = "Enter your email",
                    ) { }

                    Spacer(modifier = Modifier.height(20.dp))

                    var selectedIndex by remember { mutableStateOf(0) }
                    Tabs(
                        labels = listOf("Label1", "Label2", "Label3").toImmutableList(),
                        selectedIndex = selectedIndex,
                        onTabSelected = { selectedIndex = it }
                    )

                    Tabs(
                        labels = listOf("Label1", "Label2", "Label3").toImmutableList(),
                        selectedIndex = 1,
                        onTabSelected = { selectedIndex = it }
                    )

                    Tabs(
                        labels = listOf("Label1", "Label2", "Label3").toImmutableList(),
                        selectedIndex = 2,
                        onTabSelected = { selectedIndex = it }
                    )
                }

            }

        }
    }
}
