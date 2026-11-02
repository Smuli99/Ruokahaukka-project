package com.ruokkis

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

// Dataluokka, jossa ID mukana
data class Restaurant(
    val id: String,
    val name: String
)

@Composable
@Preview
fun RestaurantsView() {
    // kovakoodattu pari ravintolaa
    // tässä vasta tekstinä
    val restaurants = remember {
        listOf(
            Restaurant("belvedere", "Belvedere"),
            Restaurant("bistro", "Bistro"),
            Restaurant("ilokivi", "Ilokivi"),
            Restaurant("lozzi", "Lozzi"),
            Restaurant("piato", "Piato"),
            Restaurant("rentukka", "Rentukka"),
            Restaurant("syke", "Syke"),
            Restaurant("taide", "Taide"),
            Restaurant("uno", "Uno"),
            Restaurant("ylisto", "Ylistö"),
        )
    }

    // pystysuuntainen lista, piirtää alkiot ruudulle
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9F0)) // mikä väri halutaan
            .clickable {
                // Tähän tulee myöhemmin siirtymä sivulle: restaurant.id
            }
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Listassa ID:n avulla (en tiiä miten oikeesti pitäs tehdä tää esimerkki
        items(restaurants, key = { it.id }) { restaurant ->
            Text(
                text = restaurant.name,
                fontSize = 18.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

