package com.ruokkis.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
            verticalArrangement = Arrangement.spacedBy(5.dp),
            maxItemsInEachRow = 4



        ){
            var selected = true //TODO: Muuta kaikki Filltered chip, oikea kuva, laita käyttämään samaa pohjaa
            //var selected by remember {mutableStateOf(false)}
            //dokumentoi

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

//            AssistChip(
//                onClick = {},
//                label = {Text("Allergia")}
//            )
//
//            AssistChip(
//                onClick = {},
//                label = {Text("Moikka")}
//            )
//            AssistChip(
//                onClick = {},
//                label = {Text("Moikka")}
//            )
//            AssistChip(
//                onClick = {},
//                label = {Text("Moikka")}
//            )
//            AssistChip(
//                onClick = {},
//                label = {Text("Moikka")}
//            )
        }
    }
}

