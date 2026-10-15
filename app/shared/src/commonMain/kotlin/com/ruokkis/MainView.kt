package com.ruokkis

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors


import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ruokkis.model.PiatoResponse
import com.ruokkis.network.FoodListApi
import com.ruokkis.network.createHttpClient
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource


import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.layout.ContentScale
import com.ruokkis.components.BottomBar
import com.ruokkis.components.DayChooserBox
import com.ruokkis.components.FilterTagBox
import com.ruokkis.components.TopBar
import ruokkis.app.shared.generated.resources.*

val testausTeksti = "\"Date\": \"2026-10-08T00:00:00+00:00\",\n" +
        "      \"LunchTime\": \"10:30–16:30\",\n" +
        "      \"SetMenus\": [\n" +
        "        {\n" +
        "          \"SortOrder\": 6,\n" +
        "          \"Name\": \"LOUNAS\",\n" +
        "          \"Price\": null,\n" +
        "          \"Components\": [\n" +
        "            \"Falafelpihvejä (G, ILM, L, M, Veg)\",\n" +
        "            \"Tzatsikia (*, A, G, ILM, L, M, Veg)\"\n" +
        "          ]\n" +
        "        },\n" +
        "        {\n" +
        "          \"SortOrder\": 17,\n" +
        "          \"Name\": \"LOUNAS\",\n" +
        "          \"Price\": null,\n" +
        "          \"Components\": []\n" +
        "        },\n" +
        "        {\n" +
        "          \"SortOrder\": 24,\n" +
        "          \"Name\": \"LOUNAS\",\n" +
        "          \"Price\": null,\n" +
        "          \"Components\": [\n" +
        "            \"Chili-Fetapatonki - Limited edition (A, L)\",\n" +
        "            \"Kana-savupalvipatonki - Limited edition (A, L, M)\"\n" +
        "          ]\n" +
        "        },\n" +
        "        {\n" +
        "          \"SortOrder\": 26,\n" +
        "          \"Name\": \"LOUNAS\",\n" +
        "          \"Price\": null,\n" +
        "          \"Components\": [\n" +
        "            \"Perinteistä makkarakastiketta (*, A, G, L, M)\"\n" +
        "          ]\n" +
        "        },\n" +
        "        {\n" +
        "          \"SortOrder\": 28,\n" +
        "          \"Name\": \"KEITTOLOUNAS\",\n" +
        "          \"Price\": null,\n" +
        "          \"Components\": [\n" +
        "            \"Hernekeittoa (*, A, G, ILM, L, M)\"\n" +
        "          ]\n" +
        "        },\n" +
        "        {\n" +
        "          \"SortOrder\": 29,\n" +
        "          \"Name\": \"FROM THE TABLE\",\n" +
        "          \"Price\": null,\n" +
        "          \"Components\": []\n" +
        "        },\n" +
        "        {\n" +
        "          \"SortOrder\": 31,\n" +
        "          \"Name\": \"FROM THE GRILL\",\n" +
        "          \"Price\": null,\n" +
        "          \"Components\": [\n" +
        "            \"Briossi BRG pekonilla ()\",\n" +
        "            \"Ranskalaisia perunoita ()\"\n" +
        "          ]\n" +
        "        },\n" +
        "        {\n" +
        "          \"SortOrder\": 32,\n" +
        "          \"Name\": \"JÄLKIRUOKA\",\n" +
        "          \"Price\": null,\n" +
        "          \"Components\": [\n" +
        "            \"Meidän keittiön pannukakkua (A, L)\",\n" +
        "            \"Vaniljakermavaahtoa (A, G, L)\"\n" +
        "          ]\n" +
        "        }\n" +
        "      ]\n" +
        "    }"

//https://developer.android.com/develop/ui/compose/components

@Composable
@Preview
fun HomePage() {
  /*  (MaterialTheme(   colorScheme = AppColors){


    }*/



    Scaffold (
        topBar = {
           TopBar()
        },


        bottomBar = {
            BottomBar()
        },


    ){ innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            FilterTagBox()


            DayChooserBox()

            HorizontalDivider(thickness = 2.dp)


            Card(
                colors = CardDefaults.cardColors( containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier
                    .padding(10.dp)
                .size(width = 300.dp, height = 100.dp),
            ){
                Text("NamNAm ruokaa")
            }
            Card(
                colors = CardDefaults.cardColors( containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier
                    .padding(10.dp)
                    .size(width = 300.dp, height = 100.dp)
            ){
                Text("NamNAm ruokaa")
            }
            Card(
                colors = CardDefaults.cardColors( containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier
                    .padding(10.dp)
                    .size(width = 300.dp, height = 100.dp)
            ){
                Text("NamNAm ruokaa")
            }

            /*
            Button(onClick = {
                scope.launch {
                    piato = restaurantApi.getRestaurant()
                }
            }) {
                Text("Get Restaurant")
            }
            if (piato != null) {
                Text(piato.toString())
            }
        }
    }

} */

        }
    }

}

/*
            Button(onClick = {
                scope.launch {
                    piato = restaurantApi.getRestaurant()
                }
            }) {
                Text("Get Restaurant")
            }
            if (piato != null) {
                Text(piato.toString())
            }
        }
    }

} */