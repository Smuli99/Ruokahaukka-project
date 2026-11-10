package com.ruokkis

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview
fun RestaurantsView() {
    // kovakoodattu pari ravintolaa
    // tässä vasta tekstinä
    val restaurants = listOf("Belvedere", "Bistro", "Ilokivi", "Lozzi", "Rentukka", "Syke", "Taide", "Uno", "Ylistö")

    // Tallennetaan muistiin, mitkä ravintolat ovat suosikkeja (avaimena ravintolan nimi, arvona totuusarvo)
    val favorites = remember { mutableStateMapOf<String, Boolean>() }

    // pystysuuntainen lista, piirtää alkiot ruudulle
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Looppi tekee jokaisesta ravintolasta oman boksin LazyColumnin sisään
        for (name in restaurants) {
            item {
                // Tarkistetaanko onko tämä ravintola suosikki (oletuksena false)
                val isFavorite = favorites[name] == true

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Ravintolan nimi
                        Text(
                            text = name,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        IconButton(onClick = {
                            // Vaihdetaan tilaa klikatessa (jos oli true -> false, ja toisin päin)
                            favorites[name] = !isFavorite
                        }) {
                            Text(
                                // Jos on suosikki, näytetään täysi sydän ❤️, jos ei, niin pelkkä 🤍
                                text = if (isFavorite) "❤️" else "🤍",
                                fontSize = 20.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
