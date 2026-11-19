package com.ruokkis

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ruokkis.components.FooSelectionButtons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun FoodsView() {

    var searchQuery by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }

    //Tallennetaan filtteröinti valinta kaikista ruoista vai vain lemppareista
    var selectedOption by rememberSaveable {
        mutableStateOf("all")
    }

    // Listaus lempiruoista. Mahdollisesti voidaan käyttää myös oikean datan kanssa vähintään variablea
    var favouriteFoods by rememberSaveable {
        mutableStateOf(setOf<Int>())
    }

    // Ruokien filtteröinti. Peruslogiikka varmaan voidaan käyttä kun yhdistetään database mutta alustavasti testausta varten
    val filteredFoods = foods.filter { food ->

        val matchesSearch = food.name.contains(
            searchQuery,
            ignoreCase = true
        )

        val matchesFavourite =
            selectedOption == "all" || food.id in favouriteFoods

        matchesSearch && matchesFavourite
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp, 0.dp),
            inputField = {
                SearchBarDefaults.InputField(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    onSearch = {
                        expanded = false
                    },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    placeholder = { Text("Haku") }
                )
            },
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            val suggestions = foods.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            }
            Column(Modifier.verticalScroll(rememberScrollState())) {
                suggestions.forEach { food ->
                    ListItem(
                        headlineContent = { Text(food.name) },
                        modifier = Modifier
                            .clickable {
                                searchQuery = food.name
                                expanded = false
                            }
                            .fillMaxWidth()
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(filteredFoods) { food ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                       Text(
                           text = food.name
                       )
                       Button(
                           onClick = {
                                favouriteFoods = if (food.id in favouriteFoods) {
                                    favouriteFoods - food.id
                                } else {
                                    favouriteFoods + food.id
                                }
                           }
                       ) {
                           Icon(
                               imageVector = if (food.id in favouriteFoods) {
                                   Icons.Filled.Favorite
                               } else {
                                   Icons.Outlined.FavoriteBorder
                               },
                               contentDescription = "Merkiste lemppariksi"
                           )
                       }
                    }
                }
            }
        }

        FooSelectionButtons(
            selectedOption = selectedOption,
            onOptionSelected = { selectedOption = it}
        )
    }
}

/**
 * Luodaan luokka Ruoka ja luodaan kuvitteellisia ruokia testausta varten
 */
data class Food(
    val id: Int,
    val name: String
)

val foods = listOf(
    Food(1, "Chicken pasta"),
    Food(2, "Margherita pizza"),
    Food(3, "Caesar salad"),
    Food(4, "Salmon soup"),
    Food(5, "Beef burger"),
    Food(6, "Vegetable curry"),
    Food(7, "Chicken tortilla"),
    Food(8, "Pancakes")
)