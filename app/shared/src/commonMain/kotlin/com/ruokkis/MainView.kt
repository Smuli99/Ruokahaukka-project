package com.ruokkis

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
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
import androidx.compose.ui.layout.ContentScale
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
    Scaffold (
        topBar = {
            TopAppBar (
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),

                title = {
                    Text("Ruokkis")
                }
            )
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
                        onClick = { /**/}
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
                    Button(onClick = {/**/}
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

                    /*
                                        Text(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            textAlign = TextAlign.Center,
                                            text = "Bottom app bar",
                                        )*/
                }


            }

        },
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(

                modifier = Modifier
                    .padding(24.dp)
                    .size(width = 240.dp, height = 100.dp)
            ){
                Row(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()

                ){

                 /*   FilterChip(
                        onClick = { selected = !selected},
                        label = {Text("Ruokaa")},
                        selected = selected,
                    )*/

                    AssistChip(
                        onClick = {},
                        label = {Text("Allergia")}
                    )

                    AssistChip(
                        onClick = {},
                        label = {Text("Moikka")}
                    )
                }
            }
            Text(
                modifier = Modifier.padding(8.dp),
                text =
                    """
                    This is an example of a scaffold. It uses the Scaffold composable's parameters to create a screen with a simple top app bar, bottom app bar, and floating action button.

                    It also contains some basic inner content, such as this text.

                    You have pressed the floating action buttontimes.
                """.trimIndent(),
            )

        }
    }

} /*{
    MaterialTheme {
        val restaurantApi = remember { FoodListApi(createHttpClient()) }
        var piato by remember { mutableStateOf<PiatoResponse?>(null) }
        val scope = rememberCoroutineScope()

        Column (
            modifier = Modifier
                .background(Color(0xFFfdd0a2))
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray)
                    .padding(16.dp, 200.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "Ruokkis",
                    modifier = Modifier.padding(20.dp, 1.dp)
                    )
            }



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