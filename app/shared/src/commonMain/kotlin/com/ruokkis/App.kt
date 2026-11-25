package com.ruokkis

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import com.ruokkis.components.BottomBar
import com.ruokkis.components.TopBar
import com.ruokkis.network.FoodListApi
import com.ruokkis.network.createHttpClient
import kotlinx.coroutines.launch

@Composable
@Preview
fun App() {

    val httpClient = remember { createHttpClient() }

    DisposableEffect(httpClient) {
        onDispose {
            httpClient.close()
        }
    }

    val foodListApi = remember { FoodListApi(httpClient) }

    MaterialTheme(   colorScheme = AppColors){
        var pagerState = rememberPagerState (
            initialPage = 1,
            pageCount = { 3 }
        )

        val scope = rememberCoroutineScope()

        Scaffold(
            topBar = {
                TopBar()
            },
            bottomBar = {
                BottomBar(
                    currentPage = pagerState.currentPage,
                    onPageSelected = { page ->
                        scope.launch {
                            pagerState.animateScrollToPage(page)
                        }
                    }
//                    onFoodsViewClick = { selectedPage = "foods" },
//                    onHomeClick = { selectedPage = "home" },
//                    onRestaurantsViewClick = { selectedPage = "restaurants" }
                )
            }
        ) { innerPadding -> //Miksi?

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalAlignment = Alignment.Top
//            Box(
//                modifier = Modifier
//                    .padding(innerPadding)
//                    .fillMaxSize()
            ) { page ->
                when (page) {
                    0 -> FoodsView(api = foodListApi)
                    1 -> HomePage(api = foodListApi)
                    2 -> RestaurantsView(api = foodListApi)

                }
            }
        }
    }
}