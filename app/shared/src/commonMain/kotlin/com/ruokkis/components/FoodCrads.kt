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
import com.ruokkis.dto.Allergy
import com.ruokkis.dto.Food
import com.ruokkis.dto.MenuItem
import com.ruokkis.dto.MenuItemFood

@Composable
@Preview
fun FoodCards(
//    menuItem: MenuItem = MenuItem("", "", "", 1,
//        listOf(MenuItemFood("", Food("", "", "",false), 2,
//            listOf(Allergy("", "", ""))))
//    ),
    food: Food = Food("", "", "", false),
    isFavourite: Boolean = false,
    onFavouriteClick: (Food) -> Unit = {},
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
                FoodRow(
                    food = food,
                    isFavourite = isFavourite,
                    onFavouriteClick = {
                        onFavouriteClick(food)
                    }
                )
//               // Sit ku saadaan backendin dataa kutsuttua frontendiin niin saadaan ruoan kategoria/nimi näytettyä oikein
//               Text(
//                   text = food.name,
//                   style = typography.titleLarge,
//                   color = MaterialTheme.colorScheme.onSurface
//               )
//               Icon(
//                   imageVector = if (isFavourite) {
//                       Icons.Filled.Favorite
//                   } else {
//                       Icons.Outlined.FavoriteBorder
//                   },
//                   contentDescription = "Merkiste lemppariksi",
//                   modifier = Modifier
//                       .clickable { onFavouriteClick() }
//               )
           }

           HorizontalDivider()

//           // Tällei voi kutsua sit kun sijottaai noihin esimerkki tekstien tilalle ne backenditlä tulevat oikeat arvot
//           food.components.forEach { component ->
//               FoodRow(
//                   name = component.name,
//                   allergens = component.allergens,
//                   selectedFilters = selectedFilters
//               )
//           }
       }
   }
}

@Composable
@Preview
fun FoodRow(
   food: Food =  Food("", "", "",false),
    isFavourite: Boolean = food.favorite,
//   allergies: List<Allergy> = listOf(Allergy("", "", "")),
//   selectedFilters: List<String> = emptyList(),
   onFavouriteClick: () -> Unit = {}
    ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 0.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
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
        Text(text = food.name, color = MaterialTheme.colorScheme.onSurface)

//        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
//            allergies.forEach { allergy ->
//                Text(
//                    text = allergy.tag,
//                    color = if (allergy.tag in selectedFilters) {
//                        Color.Red
//                    } else {
//                        MaterialTheme.colorScheme.onSurface
//                    }
//                )
//            }
//        }
    }
}