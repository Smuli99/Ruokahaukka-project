package com.ruokkis


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ruokkis.components.DayCard
import com.ruokkis.components.DayChooserBox
import com.ruokkis.components.FilterTagBox
import com.ruokkis.network.FoodListApi
import kotlinx.datetime.TimeZone
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

// Testaus datan importit
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.minus
import kotlinx.datetime.plus
// Nämä todennäköisesti jää
import com.ruokkis.model.Restaurant
import com.ruokkis.model.RestaurantDetails
import com.ruokkis.model.WeekMenu
import com.ruokkis.model.DayMenu


//UI elementit how to.tm
//https://developer.android.com/develop/ui/compose/components

@Composable
@Preview
fun HomePage(api: FoodListApi? = null) {

//    val httpClient = createHttpClient()
//    val foodListApi = remember { FoodListApi(httpClient) }
//    val scope = rememberCoroutineScope()
//    val piato = remember { mutableStateOf<RestaurantMenu?>(null) }

    // Tallennetaan hakusana tänne, jotta hakua tehdessä voidaan oikeasti filtteröidä ruokia
    var searchQuery by rememberSaveable {
        mutableStateOf("")
    }
    // Tallennetaan valitut filtterit, jotta voidaan filtteröidä ruokia
    var selectedFilters by remember {
        mutableStateOf(setOf<String>())
    }

    var selectedDay by remember {
        mutableIntStateOf(
            Clock.System.now()
                .toLocalDateTime(TimeZone.currentSystemDefault())
                .dayOfWeek.isoDayNumber
        )
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        /**
         * annetaan filterTagBoxille nämä argumentit, jotta näiden vaikutus saadaan myös muille
         * jotka riippuvat filtteröinneistä
         */
        FilterTagBox(
            api = api,
            searchQuery = searchQuery,
            onSearchQueryChange = { searchQuery = it },
            selectedFilters = selectedFilters,
            onSelectedFiltersChange = {
                selectedFilters = it
            }
        )

        DayChooserBox(
            selectedDay = selectedDay,
            onDaySelected = { selectedDay = it }
        )

        HorizontalDivider(thickness = 2.dp)

        //TODO tähän dayCardin oikea kutsuminen kunhan saadaan oikeasti RestaurantDetails haettua backendiltä


        /**
         * Vähän testaus dataa jotta voi testata DayCardin toimintaa ilman backend dataa
         */
        val today = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date

        val monday = today.minus(
            DatePeriod(days = today.dayOfWeek.isoDayNumber - 1)
        )

        val sampleRestaurants = listOf(
            Restaurant(
                id = "1",
                name = "Piato",
                url = "",
                campus = null,
                favorite = false
            ),
            Restaurant(
                id = "2",
                name = "Lozzi",
                url = "",
                campus = null,
                favorite = false
            ),
            Restaurant(
                id = "3",
                name = "Ilokivi",
                url = "",
                campus = null,
                favorite = false
            )
        )

        val weekdays = listOf(
            "Monday", "Tuesday", "Wednesday", "Thursday",
            "Friday", "Saturday", "Sunday"
        )

        val sampleRestaurantDetails = sampleRestaurants.map { restaurant ->
            RestaurantDetails(
                restaurant = restaurant,
                weekMenu = WeekMenu(
                    id = "week-${restaurant.id}",
                    weekStart = monday,
                    days = weekdays.mapIndexed { index, weekday ->
                        DayMenu(
                            id = "${restaurant.id}-$index",
                            date = monday.plus(DatePeriod(days = index)),
                            weekday = weekday,
                            lunchTime = "10:30–13:00",
                            items = emptyList(),
                            weekMenuId = "week-${restaurant.id}"
                        )
                    }
                )
            )
        }

        val availableDates = sampleRestaurantDetails
            .flatMap { it.weekMenu?.days.orEmpty() }
            .map { it.date }
            .distinct()
            .sorted()

        val displayedDates = if (selectedDay == -1) {
            availableDates
        } else {
            availableDates.filter {
                it.dayOfWeek.isoDayNumber == selectedDay
            }
        }

        // Tässä vain testausta varten oleva kutsu
        displayedDates.forEach { date ->
            DayCard(
                date = date,
                restaurantDetails = sampleRestaurantDetails
            )
        }
    }
}
//        Button(onClick = {
//            scope.launch {
//                piato.value = foodListApi.getWeek("piato")
//            }
//        }) {
//            Text("Get Restaurant")
//        }
//        piato?.let { menu ->
//            Text(menu.toString())
//        }