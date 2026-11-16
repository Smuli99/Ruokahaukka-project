package com.ruokkis

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun RestaurantPage(
    restaurant: Restaurant, // otetaan vastaan valittu Ravintola (olio)
    onBack: () -> Unit // Funktio, jota kutsutaan takaisin-napista
) {
    // Muuttuja, joka pitää muistissa näytetäänkö päivän (0) vai viikon (1) näkymä
    var selectedView by remember { mutableStateOf(0) }

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
                text = "Aukioloajat: ${restaurant.openingHours}",
                style = MaterialTheme.typography.bodyLarge
            )

            // Painikkeet, joilla vaihdetaan päivä- ja viikkonäkymän välillä
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { selectedView = 0 }, // vaihtaa tilaksi 0 (päivä)
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Päivä")
                }
                Button(
                    onClick = { selectedView = 1 }, // vaihtaa tilaksi 1 (viikko)
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Viikko")
                }
            }

            // Näytetään sisältö sen mukaan onko valittu viikko vai päivä
            if (selectedView == 0) {
                // Mallina RestaurantCards -haaran ruokakortit
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Tämän päivän lounaat", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(8.dp))

                        // Mallisisältö
                        Text("• Tofua ja penneä limonellokastikkeessa\n• Taco broileria\n• Siskonmakkarakeittoa/n• From the table: Pepperoni pinsa")
                    }
                }
            } else {
                // Viikkonäkymä
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Koko viikon ruokalista", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Maanantai – Perjantai: Vaihtuvia lounaita.")
                    }
                }
            }
        }
    }
}