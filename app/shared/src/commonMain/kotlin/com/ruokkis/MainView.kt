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
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ){




                    Button(
                        onClick = { /**/}
                    ){
                        Text("upeeta")
                    }

                    Button(
                        onClick = { /**/} //TODO: Napista painamalla avaa koti sivun / palaa siihen
                    ){
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Image(
                                painter = painterResource(Res.drawable.home),
                                contentDescription = "Home icon",
                                modifier = Modifier.size(24.dp),
                                contentScale = ContentScale.Fit
                            )
                            Text("Koti")
                        }


                    }
                    Button(onClick = {/**/} //TODO: Napista painamalla siirtyy lempparit sivustolle
                    ){
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Image(
                                painter = painterResource(Res.drawable.heart),
                                contentDescription = "Heart",
                                modifier = Modifier.size(24.dp),
                                contentScale = ContentScale.Fit
                            )
                            Text("Lempparit")
                        }


                    }

                }


            }

        },


    ){ innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Card(
                // Nää vois ehkä siirtää omaan tiedostoonsa ja vaan kutsua täältä
                colors = CardDefaults.cardColors( containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier
                    .padding(24.dp),

                    //.size(width = 300.dp, height = 100.dp)
            ){
                FlowRow(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    maxItemsInEachRow = 4



                ){
                    var selected = true //TODO: Muuta kaikki Filltered chip, oikea kuva, laita käyttämään samaa pohjaa
                    //var selected by remember {mutableStateOf(false)}
                   FilterChip(

                        onClick = { selected = false},
                        label = {Text("Ruokaa")},
                        selected = selected,
                       leadingIcon = {
                           if (selected) {
                               Image(
                                   painter = painterResource(Res.drawable.heart),
                                   contentDescription = "Heart",
                                   modifier = Modifier.size(24.dp),
                                   contentScale = ContentScale.Fit
                               )

                           } else {

                               Image(
                                   painter = painterResource(Res.drawable.home),
                                   contentDescription = "Home",
                                   modifier = Modifier.size(24.dp),
                                   contentScale = ContentScale.Fit
                               )
                           }
                       }
                    )

                    AssistChip(
                        onClick = {},
                        label = {Text("Allergia")}
                    )

                    AssistChip(
                        onClick = {},
                        label = {Text("Moikka")}
                    )
                    AssistChip(
                        onClick = {},
                        label = {Text("Moikka")}
                    )
                    AssistChip(
                        onClick = {},
                        label = {Text("Moikka")}
                    )
                    AssistChip(
                        onClick = {},
                        label = {Text("Moikka")}
                    )
                }
            }
            var expanded = true //TODO: Järkevästi, ei näin paljon toistoa, datan lisääminen / hakeminen
            Box(
                modifier = Modifier.align(Alignment.Start)
                .padding(10.dp)
            ){
                Button(onClick = {expanded = !expanded}){
                    Text("Päivä")///moi
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false}
                ) {
                    DropdownMenuItem(
                        text= {Text("Ma")},
                        onClick = {/**/}
                    )
                    DropdownMenuItem(
                        text= {Text("Ti")},
                        onClick = {/**/}
                    )
                    DropdownMenuItem(
                        text= {Text("Ke")},
                        onClick = {/**/}
                    )
                    DropdownMenuItem(
                        text= {Text("To")},
                        onClick = {/**/}
                    )
                    DropdownMenuItem(
                        text= {Text("Pe")},
                        onClick = {/**/}
                    )
                }

            }
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