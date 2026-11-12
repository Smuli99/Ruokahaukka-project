package com.ruokkis.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun FoodCards() {
   Card(
       modifier = Modifier.fillMaxWidth(),
       colors = CardDefaults.cardColors(
           containerColor = MaterialTheme.colorScheme.surface
       )
   ) {
       Column(
           modifier = Modifier
               .padding(5.dp),
           verticalArrangement = Arrangement.spacedBy(5.dp)
       ) {
           // Sit ku saadaan backendin dataa kutsuttua frontendiin niin saadaan ruoan kategoria/nimi näytettyä oikein
           Text(
               text= "Esimerkki ruoka",
               style= typography.titleMedium,
               color = MaterialTheme.colorScheme.onSurface
               )

           // Tällei voi kutsua sit kun sijottaai noihin esimerkki tekstien tilalle ne backenditlä tulevat oikeat arvot
           FoodRow(
               name = "Testi mielessä vaan bäärgeri",
               allergens = listOf("G", "L"),
               selectedFilters = setOf("L")
           )
       }
   }
}

@Composable
@Preview
fun FoodRow(
        name: String = "",
        allergens: List<String> = listOf("", ""),
        selectedFilters: Set<String> = setOf("")
    ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = name, color = MaterialTheme.colorScheme.onSurface)

        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            allergens.forEach { allergen ->
                Text(
                    text = allergen,
                    color = if (allergen in selectedFilters) {
                        Color.Red
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    }
                )
            }
        }
    }
}