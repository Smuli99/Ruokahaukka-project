package com.ruokkis

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ruokkis.network.FoodListApi
import com.ruokkis.network.createHttpClient
import com.ruokkis.dto.Restaurant

@Composable
@Preview
fun RestaurantsView() {
    // tässä vasta tekstinä lopussa jotenkin id:n kautta nimet ja aukioloajat ??
//    val restaurants = listOf("Belvedere", "Bistro", "Ilokivi", "Lozzi", "Piato", "Rentukka", "Syke", "Taide", "Uno", "Ylistö")
//    val openingHours = listOf("8.00 - 16.00", "10.30 - 13.00", "9.00 - 16.15", "10.30 - 18.00", "10.30 - 16.30", "11.00 - 19.00", "11.00 - 13.30", "10.30 - 18.00", "10.30 - 14.00", "10.30 - 14.30")

    val client = remember { createHttpClient() }
    val foodListApi = remember { FoodListApi(client) }

    var restaurants by remember { mutableStateOf (emptyList<Restaurant>())}
    // Ladatessa data backendiltä pidetään sovellus "lataus" tilassa
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null)}

    // Tallennetaan muistiin, mitkä ravintolat ovat suosikkeja (avaimena ravintolan nimi, arvona totuusarvo)
    val favorites = remember { mutableStateMapOf<String, Boolean>() }

    // Tämä avulla ei tarvitse lähettää toista kutsua backendille joka kerta kun ravintola sivulle mennään
    LaunchedEffect(Unit) {
        try {
            restaurants = foodListApi.getRestaurants()
        } catch (e: Exception) {
            error = e.message
        } finally {
            isLoading = false
        }
    }



    var selectedRestaurant by remember {
        mutableStateOf<Restaurant?>(null)
    }

    // pystysuuntainen lista, piirtää alkiot ruudulle
    LazyColumn( // Columnista kevyempi versio
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = restaurants,
            key = { it.id }
        ) { restaurant ->
            val isFavorite = favorites[restaurant.id] == true

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
                    Column {
                        Text(
                            text = restaurant.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    IconButton(onClick = {
                        // Vaihdetaan tilaa klikatessa (jos oli true -> false, ja toisin päin)
                        favorites[restaurant.id] = !isFavorite
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

/**
 *  Tässtä alaspäin turvaan siirrettyä logiikka edellisestä toteutuksesta
 */

//    if(selectedRestaurant != null) {
//
//        RestaurantPage(
//            restaurant = selectedRestaurant!!,
//            onBack = {
//                selectedRestaurant = null
//            }
//        )
//    } else {
//            // Looppi tekee jokaisesta ravintolasta oman boksin LazyColumnin sisään
//            for (i in restaurants.indices) {
//                val name = restaurants[i]
//                val hours = openingHours[i]
//                // MUUTOS: Luodaan restaurant-muuttuja tälle kierrokselle, jotta Cardin onClick löytää sen
//                val restaurant = Restaurant(i + 1, name.toString(), hours, "Ravintolan tiedot")
//
//                val isFavorite = favorites[name.toString()] == true // Tarkistetaanko onko tämä ravintola suosikki (oletuksena false)
//                item {
//                    Card(
//                        onClick = {selectedRestaurant = restaurant},
//                        modifier = Modifier.fillMaxWidth(),
//                    ) {
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(16.dp),
//                            horizontalArrangement = Arrangement.SpaceBetween,
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//
//                            // Ravintolan nimi
//                            Text(
//                                text = name.toString(),
//                                style = MaterialTheme.typography.titleMedium,
//                            )
//                            // Ravintolan aukioloaika (pienempi fontti)
//                            Text(
//                                text = " $hours",
//                                style = MaterialTheme.typography.bodySmall,
//                                color = MaterialTheme.colorScheme.onSurface
//                            )
//
//                    }
//                }
//            }
//    }



//data class Restaurant(
//    val id: Int,
//    val name: String,
//    val openingHours: String,
//    val description: String
//)
//
//val restaurants = listOf(
//    Restaurant(1, "Belvedere", "8.00 - 16.00", "Restaurant information"),
//    Restaurant(2, "Bistro", "10.30 - 13.00", "Restaurant information"),
//    Restaurant(3, "Ilokivi", "9.00 - 16.15", "Restaurant information"),
//    Restaurant(4, "Lozzi", "10.30 - 18.00", "Restaurant information"),
//    Restaurant(5, "Piato", "10.30 - 16.30", "Restaurant information"),
//    Restaurant(6, "Rentukka", "11.00 - 19.00", "Restaurant information"),
//    Restaurant(7, "Syke", "11.00 - 13.30", "Restaurant information"),
//    Restaurant(8, "Taide", "10.30 - 18.00", "Restaurant information"),
//    Restaurant(9, "Uno", "10.30 - 14.00", "Restaurant information"),
//    Restaurant(10, "Ylistö", "10.30 - 14.30", "Restaurant information")
//)
