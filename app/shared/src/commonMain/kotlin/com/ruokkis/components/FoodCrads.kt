package com.ruokkis.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ruokkis.Food
import com.ruokkis.FoodComponent

@Composable
@Preview
fun FoodCards(
    food: Food = Food(
        id = 0,
        name = "Example food",
        components = listOf(
            FoodComponent("Example component", listOf("L", "G")),
            FoodComponent("Another component", listOf("A"))
        )
    ),
    selectedFilters: String = "",
    isFavourite: Boolean = false,
    onFavouriteClick: () -> Unit = {},
) {
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
           Row(
               modifier = Modifier.fillMaxWidth(),
               horizontalArrangement = Arrangement.SpaceBetween,
               verticalAlignment = Alignment.CenterVertically
           ) {
               // Sit ku saadaan backendin dataa kutsuttua frontendiin niin saadaan ruoan kategoria/nimi näytettyä oikein
               Text(
                   text = food.name,
                   style = typography.titleLarge,
                   color = MaterialTheme.colorScheme.onSurface
               )
               Icon(
                   imageVector = if (isFavourite) {
                       Icons.Filled.Favorite
                   } else {
                       Icons.Outlined.FavoriteBorder
                   },
                   contentDescription = "Merkiste lemppariksi",
                   modifier = Modifier
                       .clickable { onFavouriteClick() }
               )
           }

           HorizontalDivider()

           // Tällei voi kutsua sit kun sijottaai noihin esimerkki tekstien tilalle ne backenditlä tulevat oikeat arvot
           food.components.forEach { component ->
               FoodRow(
                   name = component.name,
                   allergens = component.allergens,
                   selectedFilters = selectedFilters
               )
           }
       }
   }
}

@Composable
@Preview
fun FoodRow(
    name: String = "",
    allergens: List<String> = listOf("", ""),
    selectedFilters: String = ""
    ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 0.dp),
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