package com.ruokkis

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.ruokkis.components.FoodCards

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

        val matchesSearch =
            food.name.contains(searchQuery, ignoreCase = true) ||
            food.components.any { component ->
                component.name.contains(searchQuery, ignoreCase = true)
            }

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
            val suggestions = foods
                .filter { food ->
                    selectedOption == "all" || food.id in favouriteFoods
                }
                .flatMap { food ->
                food.components
                    .filter { component ->
                        component.name.contains(searchQuery, ignoreCase = true)
                    }
                    .map { component ->
                        food to component
                    }
            }
            Column(Modifier.verticalScroll(rememberScrollState())) {
                suggestions.forEach { (food, component) ->
                    ListItem(
                        headlineContent = { Text(component.name) },
                        supportingContent = { Text(food.name) },
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
                FoodCards(
                    food = food,
                    selectedFilters = selectedOption,
                    isFavourite = food.id in favouriteFoods,
                    onFavouriteClick = {
                        favouriteFoods = if (food.id in favouriteFoods) {
                            favouriteFoods - food.id
                        } else {
                            favouriteFoods + food.id
                        }
                    }
                )
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
    val name: String,
    val components: List<FoodComponent>
)

data class FoodComponent(
    val name: String,
    val allergens: List<String>
)
val foods = listOf(
    Food(
        id = 1,
        name = "Lounas",
        components = listOf(
            FoodComponent("Chicken pasta", listOf("L", "G")),
            FoodComponent("Fresh salad", listOf("G")),
            FoodComponent("Bread", listOf("A", "L"))
        )
    ),
    Food(
        id = 2,
        name = "Lounas",
        components = listOf(
            FoodComponent("Pizza with tomato sauce", listOf("A")),
            FoodComponent("Mozzarella cheese", listOf("L")),
            FoodComponent("Fresh basil", emptyList())
        )
    ),
    Food(
        id = 3,
        name = "Lounas",
        components = listOf(
            FoodComponent("Chicken", listOf("G")),
            FoodComponent("Romaine lettuce", emptyList()),
            FoodComponent("Caesar dressing", listOf("L", "M")),
            FoodComponent("Croutons", listOf("A"))
        )
    ),
    Food(
        id = 4,
        name = "Keitto lounas",
        components = listOf(
            FoodComponent("Salmon soup", listOf("L")),
            FoodComponent("Rye bread", listOf("A")),
        )
    ),
    Food(
        id = 5,
        name = "From the grill",
        components = listOf(
            FoodComponent("Beef patty", listOf("G")),
            FoodComponent("Burger bun", listOf("A")),
            FoodComponent("Cheese", listOf("L")),
            FoodComponent("French fries", listOf("G"))
        )
    )
)
