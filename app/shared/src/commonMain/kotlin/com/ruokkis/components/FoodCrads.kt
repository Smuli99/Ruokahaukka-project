package com.ruokkis.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun FoodCards() {

    LazyColumn {
        item {
            Card(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxSize()
            ){
                Text(text = "ruoan tyyppi")
            }
        }
    }
}