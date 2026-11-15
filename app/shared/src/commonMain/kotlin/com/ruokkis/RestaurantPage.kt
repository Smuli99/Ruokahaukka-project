package com.ruokkis

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Takaisin"
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
                Text(
                    text = "Tähän tulee päivän ruokakortit",
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                Text(
                    text = "Tähän tulee viikon ruokalista",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}