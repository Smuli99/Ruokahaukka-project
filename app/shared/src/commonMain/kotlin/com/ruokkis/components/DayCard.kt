package com.ruokkis.components

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
import com.ruokkis.model.RestaurantDetails
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock


@Composable
@Preview
fun DayCard(
    date: LocalDate = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()).date,
    restaurantDetails: List<RestaurantDetails> = emptyList()
) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = date.toString(),
                style = MaterialTheme.typography.titleLarge
            )

            HorizontalDivider()

            restaurantDetails.forEach { details ->
                val dayMenu = details.weekMenu?.days
                    ?.find { it.date == date }

                if (dayMenu != null) {
                    RestaurantCards(
                        restaurant = details.restaurant,
                        dayMenu = dayMenu
                    )
                }
            }
        }
    }
}