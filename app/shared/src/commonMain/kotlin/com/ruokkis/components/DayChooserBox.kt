package com.ruokkis.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.* // lisätty

@Composable
@Preview
fun DayChooserBox() {
    var expanded by remember {mutableStateOf(false)} //lisätty verrattuna MainViewissä olleeseen

    Box(
        modifier = Modifier
            .padding(10.dp)
    ){
        Button(onClick = {expanded = !expanded}){
            Text("Päivä")///moi
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false}
        ) {
            DropdownMenuItem(
                text= {Text("Ma")},
                onClick = {/**/}
            )
            DropdownMenuItem(
                text= {Text("Ti")},
                onClick = {/**/}
            )
            DropdownMenuItem(
                text= {Text("Ke")},
                onClick = {/**/}
            )
            DropdownMenuItem(
                text= {Text("To")},
                onClick = {/**/}
            )
            DropdownMenuItem(
                text= {Text("Pe")},
                onClick = {/**/}
            )
        }

    }
}