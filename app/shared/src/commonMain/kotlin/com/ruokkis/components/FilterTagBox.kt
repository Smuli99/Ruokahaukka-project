package com.ruokkis.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
@Preview //Jotta tällä tavalla tehdessä preview toimii täytyy antaa default arvot jokaiselle parametrille, mutta nämä ei vaikuta toiminnallisuuteen
fun FilterTagBox(
    searchQuery: String = "",
    onSearchQueryChange: (String) -> Unit = {},
    selectedFilters: Set<String> = emptySet(),
    onSelectedFiltersChange: (Set<String>) -> Unit = {}
){

    val filtersList = listOf("G", "L", "M", "A", "VS", "Veg", "Vl", "MUNATON", "SOIJATON", "VOIMSUOLAI", "LAKTOVEGET",
                                "ERITTVHG", "VEGAN", "SYDNMERKK", "ILMASTO", "ILM", "SILAINEET", "LAKTOOVOVE", "VRIAINEET")

    Card(
        colors = CardDefaults.cardColors( containerColor = MaterialTheme.colorScheme.primaryContainer),
        modifier = Modifier
            .padding(24.dp)

    ){
        Text(
            text = "Rajaa/hae",
            modifier = Modifier.fillMaxWidth()
                .padding(0.dp, 16.dp, 16.dp),
            textAlign = TextAlign.Center
        )
        FlowRow(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalArrangement= Arrangement.spacedBy(5.dp)
        ){
            for(filter in filtersList) {
                val selected = filter in selectedFilters
                FilterChip(
                    /**
                     * onClick toiminnallisuuteen lisätty toimintoja, jotta mainview tietää mitkä ovat valittuja
                     * jotta saadaan oikein filtteröityä ruokia
                     */
                    onClick = {
                        onSelectedFiltersChange(
                            if (selected) {
                                selectedFilters - filter
                            } else {
                                selectedFilters + filter
                            }
                        )
                    },
                    label = {Text(filter)},
                    selected = selected,
                    leadingIcon = if (selected) {
                        {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = "Done icon",
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        }
                    } else {
                        null
                    }
                )
            }
        }
        // Hakukenttä joka pystyy vaikuttamaan RestaurantsCardien sisältöön
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding( 10.dp, 5.dp, 10.dp, 20.dp),
            placeholder = { Text("Hae") },
            singleLine = true
        )
    }
}
