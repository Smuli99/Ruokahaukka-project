package com.ruokkis

import androidx.compose.foundation.isSystemInDarkTheme
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
import com.ruokkis.network.FoodListApi
import com.ruokkis.network.createHttpClient

@Composable
@Preview
fun App() {

    val httpClient = remember { createHttpClient() }

    var selectedTheme by rememberSaveable {
        mutableStateOf(AppTheme.SYSTEM)
    }

    val darkTheme = when (selectedTheme) {
        AppTheme.LIGHT -> false
        AppTheme.DARK -> true
        AppTheme.SYSTEM -> isSystemInDarkTheme()
    }

    DisposableEffect(httpClient) {
        onDispose {
            httpClient.close()
        }
    }

    val foodListApi = remember { FoodListApi(httpClient) }

    MaterialTheme(
        colorScheme = if (darkTheme) AppColorsDark else AppColors
    ){
        var selectedPage by rememberSaveable {
            mutableStateOf("home")
        }

        Scaffold(
            topBar = {
                TopBar(
                    selectedTheme = selectedTheme,
                    onThemeChange = { selectedTheme = it}
                )
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
                    "home" -> HomePage(api = foodListApi)
                    "foods" -> FoodsView(api = foodListApi)
                    "restaurants" -> RestaurantsView(api = foodListApi)

                }
            }
        }
    }
}

enum class AppTheme {
    LIGHT,
    DARK,
    SYSTEM
}