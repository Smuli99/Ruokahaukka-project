package com.ruokkis.components

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import ruokkis.app.shared.generated.resources.Res
import ruokkis.app.shared.generated.resources.heart
import ruokkis.app.shared.generated.resources.home


@Composable
@Preview
fun FilterTagBox(){

    val filtersList = arrayOf("G", "L", "M", "A", "VS", "Veg", "Vl", "MUNATON", "SOIJATON", "VOIMSUOLAI", "LAKTOVEGET",
                                "ERITTVHG", "VEGAN", "SYDNMERKK", "ILMASTO", "ILM", "SILAINEET", "lAKTOOVOVE", "VRIAINEET")

    Card(
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
            verticalArrangement = Arrangement.spacedBy(5.dp)
//            maxItemsInEachRow = 4



        ){
            for(filter in filtersList) {
                var selected by remember { mutableStateOf(false) } // Tämän pitää olla for loopin sisällä, jotta jokainen chip saa oman tilan oikein eikä jaettua
                FilterChip(
                    onClick = {selected = !selected },
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
    }
}


// Vanha koodi jonka voi poistaa ennen mergeä, mutta muutoin toimii esimerkkinä
//TODO: poista kun saadaan kokonaan valmiiksi
/**

            FilterChip(

                onClick = { selected = false},
                label = {Text("Ruokaa")},
                selected = selected,
                leadingIcon = {
                    if (selected) {
                        Image(
                            painter = painterResource(Res.drawable.heart),
                            contentDescription = "Heart", // huom. pitää muokkaa
                            modifier = Modifier.size(24.dp),
                            contentScale = ContentScale.Fit
                        )

                    } else {

                        Image(
                            painter = painterResource(Res.drawable.home),
                            contentDescription = "Home", // huom. pitää muokkaa
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
            )*/

