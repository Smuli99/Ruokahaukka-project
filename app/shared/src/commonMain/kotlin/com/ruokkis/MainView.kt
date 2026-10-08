package com.ruokkis

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ruokkis.model.PiatoResponse
import com.ruokkis.network.FoodListApi
import com.ruokkis.network.createHttpClient
import kotlinx.coroutines.launch


@Composable
@Preview
fun HomePage() {
    MaterialTheme {
        val restaurantApi = remember { FoodListApi(createHttpClient()) }
        var piato by remember { mutableStateOf<PiatoResponse?>(null) }
        val scope = rememberCoroutineScope()

        Column (
            modifier = Modifier
                .background(Color(0xFFfdd0a2))
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray)
                    .padding(16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text("Hello World!")
            }
            Button(onClick = {
                scope.launch {
                    piato = restaurantApi.getRestaurant()
                }
            }) {
                Text("Get Restaurant")
            }
            if (piato != null) {
                Text(piato.toString())
            }
        }
    }

}