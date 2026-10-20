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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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

    val httpClient = createHttpClient()
    val foodListApi = remember { FoodListApi(httpClient) }
    val scope = rememberCoroutineScope()
    val piato = remember {mutableStateOf<RestaurantMenu?>(null)}




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



            Button(onClick = {
                scope.launch {
                    piato.value = foodListApi.getWeek("piato")
                }
            }) {
                Text("Get Restaurant")
            }
            piato?.let { menu ->
                Text(menu.toString())
            }
        }





    }

}
