package com.ruokkis

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ruokkis.model.DayMenu
import com.ruokkis.model.Restaurant
import com.ruokkis.model.RestaurantDetails
import com.ruokkis.network.FoodListApi
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun RestaurantPage(
    restaurant: Restaurant = Restaurant("1", "Belvedere", "8.00 - 16.00", "Restaurant information", false), // otetaan vastaan valittu Ravintola (olio)
    api: FoodListApi? = null,
    onBack: () -> Unit = {} // Funktio, jota kutsutaan takaisin-napista
) {
    // Muuttuja, joka pitää muistissa näytetäänkö päivän (0) vai viikon (1) näkymä
    var selectedView by remember { mutableStateOf(0) }

    var todayMenu by remember(restaurant.id) {
        mutableStateOf<DayMenu?>(null)
    }

    var weekMenu by remember(restaurant.id) {
        mutableStateOf<RestaurantDetails?>(null)
    }

    var isLoading by remember {
        mutableStateOf(true)
    }

    var error by remember {
        mutableStateOf<String?>(null)
    }

    LaunchedEffect(api, restaurant.id) {
        if (api != null) {
            try {
                todayMenu = api.getToday(restaurant.id)
                weekMenu = api.getWeek(restaurant.id)
            } catch (e: Exception) {
                error = e.message
            } finally {
                isLoading = false
            }
        } else {
            isLoading = false
        }
    }

    // Scaffold luo perusrakenteen, jossa mukana yläpalkki (topAppBar)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(restaurant.name) }, // Näyttää ravintolan nimen yläpalkissa
                navigationIcon = {
                    // Takaisin-nappi yläalkin vasemmassa reunassa (MyJyu:ssa kanssa noin)
                    TextButton(onClick = onBack) {
                        Text(
                            text = "Takaisin",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        // Sivun varsinainen sisältö pinotaan pystysuunnassa Columnilla
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Huomioi yläpalkin jättämän tilan
                .padding(16.dp), // Jättää reunoilla tyhjää tilaa
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Ravintolan perustiedot
            Text(
                text = "",
//                text = "Aukioloajat: ${restaurant.openingHours}",
                style = MaterialTheme.typography.bodyLarge
            )

            // Segmentoitu valitsin päivä- ja viikkonäkymän välille
            SingleChoiceSegmentedButtonRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                SegmentedButton(
                    selected = selectedView == 0,
                    onClick = { selectedView = 0 },
                    shape = RectangleShape
                ) {
                    Text("Päivä")
                }
                gitSegmentedButton(
                    selected = selectedView == 1,
                    onClick = { selectedView = 1 },
                    shape = RectangleShape
                ) {
                    Text("Viikko")
                }
            }

            // Näytetään sisältö sen mukaan ladataanko, onko virhe vai mikä näkymä on valittu
            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                error != null -> {
                    Box(
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Virhe: $error", color = MaterialTheme.colorScheme.error)
                    }
                }
                selectedView == 0 -> {
                    // Päivänäkymä
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Tämän päivän lounas", style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(8.dp))

                            if (todayMenu == null || todayMenu?.items.isNullOrEmpty()) {
                                Text("Ei lounastietoja tälle päivälle.")
                            } else {
                                todayMenu?.items?.forEach { menuItem ->
                                    Column(modifier = Modifier.padding(vertical = 4.dp)) {
                                        menuItem.foods.forEach { foodItem ->
                                            Text("• ${foodItem.food.name}")
                                        }
                                        if (!menuItem.price.isNullOrEmpty()) {
                                            Text(
                                                text = "Hinta: ${menuItem.price}",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.secondary
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                else -> {
                    // Viikkonäkymä
                    val days = weekMenu?.weekMenu?.days

                    if (days.isNullOrEmpty()) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = "Ei viikoittaista ruokalistaa saatavilla.")
                            }
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(days) { day ->
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text(
                                            text = "${day.weekday} (${day.date})",
                                            style = MaterialTheme.typography.titleSmall
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))

                                        if (day.items.isEmpty()) {
                                            Text("Ei ruokalistaa", style = MaterialTheme.typography.bodySmall)
                                        } else {
                                            day.items.forEach { menuItem ->
                                                Column(modifier = Modifier.padding(vertical = 2.dp)) {
                                                    menuItem.foods.forEach { foodItem ->
                                                        Text("• ${foodItem.food.name}", style = MaterialTheme.typography.bodyMedium)
                                                    }
                                                    val currentPrice = menuItem.price
                                                    if (!currentPrice.isNullOrEmpty()) {
                                                        Text(
                                                            text = currentPrice,
                                                            style = MaterialTheme.typography.bodySmall,
                                                            color = MaterialTheme.colorScheme.secondary
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }



            // Näytetään sisältö sen mukaan onko valittu viikko vai päivä
           // if (selectedView == 0) {
                // Mallina RestaurantCards -haaran ruokakortit
               // Card(
                   // colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    // modifier = Modifier
                   //     .wrapContentHeight()
                 //       .fillMaxWidth()
               // ) {
                    // Column(modifier = Modifier.padding(16.dp)) {
                      //  Text(text = "Tämän päivän lounaat", style = MaterialTheme.typography.titleMedium)
                    //    Spacer(modifier = Modifier.height(8.dp))

                        // Mallisisältö
                  //      Text("• Tofua ja penneä limonellokastikkeessa\n• Taco broileria\n• Siskonmakkarakeittoa\n• From the table: Pepperoni pinsa")
                //    }
              //  }
            //} else {
                // Viikkonäkymä
                //Card(
                    //colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                  //  modifier = Modifier.fillMaxWidth()
                //) {
                    //Column(modifier = Modifier.padding(16.dp)) {
                       // Text(text = "Koko viikon ruokalista", style = MaterialTheme.typography.titleMedium)
                     //   Spacer(modifier = Modifier.height(8.dp))
                   //     Text("Maanantai – Perjantai: Vaihtuvia lounaita.")
                  //  }
               //  }
            // }
        }
    }
}