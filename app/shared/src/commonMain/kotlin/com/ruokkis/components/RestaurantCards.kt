package com.ruokkis.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ruokkis.model.DayMenu
import com.ruokkis.model.Restaurant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

@Composable
@Preview
fun RestaurantCards(
    restaurant: Restaurant = Restaurant("", "Piato", "", null, false),
    dayMenu: DayMenu = DayMenu("", Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date, "Monday", null, emptyList(), "")
) {

    //MUUTA
    //TODO: Tee joku pohja, ettei tartte toistaa. (dynaaminen)
    // HAe oikeesti teksti

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        colors = CardDefaults.cardColors( containerColor = MaterialTheme.colorScheme.primaryContainer)
    ){
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = restaurant.name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            HorizontalDivider()

            //TODO tähän oikeat ruoka kortit kuhan ne saa kasaan backend datasta
//            Text("Piato", style = MaterialTheme.typography.titleLarge)
//            FoodCards()
        }
    }
//    Card(
//        colors = CardDefaults.cardColors( containerColor = MaterialTheme.colorScheme.primaryContainer),
//        modifier = Modifier
//            .padding(10.dp)
//            .size(width = 300.dp, height = 100.dp)
//    ){
//        Text("NamNAm ruokaa")
//    }
//    Card(
//        colors = CardDefaults.cardColors( containerColor = MaterialTheme.colorScheme.primaryContainer),
//        modifier = Modifier
//            .padding(10.dp)
//            .size(width = 300.dp, height = 100.dp)
//    ){
//        Text("NamNAm ruokaa")
//    }
}