package com.ruokkis.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun RestaurantCards(){

    //MUUTA
    //TODO: Tee joku pohja, ettei tartte toistaa. (dynaaminen)
    // HAe oikeesti teksti


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
}