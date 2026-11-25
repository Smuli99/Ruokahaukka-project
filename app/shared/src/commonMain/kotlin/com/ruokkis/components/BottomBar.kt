package com.ruokkis.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import ruokkis.app.shared.generated.resources.Res
import ruokkis.app.shared.generated.resources.heart
import ruokkis.app.shared.generated.resources.home
import ruokkis.app.shared.generated.resources.restaurant

@Composable
@Preview
fun BottomBar(
    currentPage: Int = 1,
    onPageSelected: (Int) -> Unit = {}
//    onFoodsViewClick: () -> Unit = {},
//    onHomeClick: () -> Unit = {},
//    onRestaurantsViewClick: () -> Unit = {}
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
    ) {
       NavigationBarItem(
           selected = currentPage == 0,
           onClick = { onPageSelected(0) },
           icon = {
               Image(
                   painter = painterResource(Res.drawable.restaurant),
                   contentDescription = "Foods icon",
                   modifier = Modifier.size(24.dp),
                   contentScale = ContentScale.Fit
               )
           },
           label = { Text("Ruoat") }
       )
       NavigationBarItem(
           selected = currentPage == 1,
           onClick = { onPageSelected(1) },
           icon = {
               Image(
                   painter = painterResource(Res.drawable.home),
                   contentDescription = "Home icon",
                   modifier = Modifier.size(24.dp),
                   contentScale = ContentScale.Fit
               )
           },
           label = { Text("Koti") }
       )

        NavigationBarItem(
            selected = currentPage == 2,
            onClick = { onPageSelected(2) },
            icon = {
                Image(
                    painter = painterResource(Res.drawable.heart),
                    contentDescription = "Heart",
                    modifier = Modifier.size(24.dp),
                    contentScale = ContentScale.Fit
                )
            },
            label = { Text("Ravintolat") }
       )
    }
//    BottomAppBar(
//        containerColor = MaterialTheme.colorScheme.primaryContainer,
//        contentColor = MaterialTheme.colorScheme.primary,
//    ) {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceEvenly,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            // 1. Ruoat-nappi
//            Button(onClick = onFoodsViewClick) {
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Image(
//                        painter = painterResource(Res.drawable.restaurant),
//                        contentDescription = "Foods icon",
//                        modifier = Modifier.size(24.dp),
//                        contentScale = ContentScale.Fit
//                    )
//                    Text("Ruoat")
//                }
//            } // <-- Tässä ensimmäinen nappi sulkeutuu oikein
//
//            // 2. Koti-nappi (nyt omana erillisenä napikseen)
//            Button(onClick = onHomeClick) {
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Image(
//                        painter = painterResource(Res.drawable.home),
//                        contentDescription = "Home icon",
//                        modifier = Modifier.size(24.dp),
//                        contentScale = ContentScale.Fit
//                    )
//                    Text("Koti")
//                }
//            } // <-- Tässä toinen nappi sulkeutuu
//
//            // 3. Ravintolat-nappi (omana erillisenä napikseen)
//            Button(onClick = onRestaurantsViewClick) {
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Image(
//                        painter = painterResource(Res.drawable.heart),
//                        contentDescription = "Heart",
//                        modifier = Modifier.size(24.dp),
//                        contentScale = ContentScale.Fit
//                    )
//                    Text("Ravintolat")
//                }
//            }
//        }
//    }
}