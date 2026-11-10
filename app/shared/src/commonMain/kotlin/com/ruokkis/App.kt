package com.ruokkis

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ruokkis.components.BottomBar
import com.ruokkis.components.TopBar

@Composable
@Preview
fun App() {
    MaterialTheme(   colorScheme = AppColors){
        var selectedPage by rememberSaveable {
            mutableStateOf("home")
        }

        Scaffold(
            topBar = {
                TopBar()
            },
            bottomBar = {
                BottomBar(
                    onFoodsViewClick = { selectedPage = "foods" },
                    onHomeClick = { selectedPage = "home" },
                    onRestaurantsViewClick = { selectedPage = "restaurants" }
                )
            }
        ) { innerPadding -> //Miksi?

            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                when (selectedPage) {
                    "home" -> HomePage()
                    "foods" -> FoodsView()
                    "restaurants" -> {}//TODO: Kunhan saadaan ravintoloiden sivu valmiiksi lisätään sekin tähän samoin kuin yllä

                }
            }
        }
    }
}