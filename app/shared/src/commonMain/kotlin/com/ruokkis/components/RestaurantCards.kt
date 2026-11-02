package com.ruokkis.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CheckboxDefaults.colors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview
fun RestaurantCards(){

    //MUUTA
    //TODO: Tee joku pohja, ettei tartte toistaa. (dynaaminen)
    // HAe oikeesti teksti

    //Lista id yms. / nimet etc etc -> stringinä cardeille yms.

    // Restaurant name
    // Daily menu

    Card(
        colors = CardDefaults.cardColors( containerColor = MaterialTheme.colorScheme.primaryContainer),
        modifier = Modifier
            .wrapContentHeight()
            .padding(10.dp)
            .width(300.dp)


    ){
        Column(
            modifier = Modifier.padding(8.dp)
        ){
            Text("Piatoo", fontSize = 20.sp)
            Card( colors = CardDefaults.cardColors( containerColor = MaterialTheme.colorScheme.secondaryContainer),
                modifier = Modifier
                    .wrapContentHeight()
                   .padding(10.dp)
                    .width(300.dp)

            ){
                Column(
                    //modifier = Modifier.padding(8.dp)
                ){
                    Text("Tofua ja penneä limonellokastikkeessa\n" +
                            "Lounas\n" +
                            "Taco broileria\n" +
                            "Keitto\n" +
                            "Siskonmakkarakeittoa\n" +
                            "From the table\n" +
                            "Pepperoni pinsa\n" +
                            "Piaton salaatti- ja keittobuffe", fontSize = 14.sp)

                }
            }

            }

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
}