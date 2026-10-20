package com.ruokkis


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ruokkis.components.BottomBar
import com.ruokkis.components.DayChooserBox
import com.ruokkis.components.FilterTagBox
import com.ruokkis.components.RestaurantCards
import com.ruokkis.components.TopBar
import com.ruokkis.model.RestaurantMenu
import com.ruokkis.network.FoodListApi
import com.ruokkis.network.createHttpClient
import kotlinx.coroutines.launch


//UI elementit how to.tm
//https://developer.android.com/develop/ui/compose/components

@Composable
@Preview
fun HomePage() {

    // remember pitää käyttää muuttuja.value, by ohittaa sen
    val foodListApi = remember { FoodListApi(createHttpClient()) }
    var menu by remember { mutableStateOf<RestaurantMenu?>(null) }
    var showMenu by remember { mutableStateOf(false) }

    /**
     * Ajetaan vain kerran sovelluksen avatessa
     * param: Unit -> ei ajeta uudestaan, voi lisätä jonkin parametrin,
     * minkä avulla data haetaan uudestaan, esim viikon vaihtuessa niin
     * saadaan aina oikea menu
     */
    LaunchedEffect(Unit) {
        menu = foodListApi.getWeek("piato")
    }

    Scaffold (
        topBar = {
           TopBar()
        },


        bottomBar = {
            BottomBar()
        },


    ){ innerPadding -> //Miksi?
        Column(
            modifier = Modifier
                .padding(innerPadding).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            FilterTagBox() //TODO dynaaminen toteutus

            DayChooserBox() //TODO Riviksi, lisää la + su

            HorizontalDivider(thickness = 2.dp)

            RestaurantCards() //TODO Tee pohja, Hae tiedot



            Button(onClick = { showMenu = !showMenu }) {
                Text(if (showMenu) "Hide menu" else "Show menu")
            }

            if (showMenu) {
                menu?.days?.forEach { day ->
                    Text(day.date)
                    day.meals.forEach { meal ->
                        var price = meal.prices
                        if (price == null) price = "Price not available"
                        Text(price)
                        meal.dishes.forEach { dish ->
                            Text(dish)
                        }
                    }
                }
            }
        }
    }
}
