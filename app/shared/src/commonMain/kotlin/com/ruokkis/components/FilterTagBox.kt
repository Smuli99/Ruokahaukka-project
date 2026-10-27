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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
@Preview
fun FilterTagBox(){

    val filtersList = arrayOf("G", "L", "M", "A", "VS", "Veg", "Vl", "MUNATON", "SOIJATON", "VOIMSUOLAI", "LAKTOVEGET",
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
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)



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
        //TODO: Lisää hakukenttä 
    }
}
