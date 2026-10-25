package com.ruokkis.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
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
    val selectedDays = remember {
        mutableStateListOf(false, false, false, false, false, false, false, false)
    }
    val days = listOf("Ma","Ti","Ke","To","Pe","La","Su","All")
    //korvaa All jollain väkäsellä
    //Pitää tehdä logiikka, jolla All -> Deslect otehrs / Select All)

    Box(
        modifier = Modifier
            .padding(5.dp)
    ){
       MultiChoiceSegmentedButtonRow{
           days.forEachIndexed {index,label ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = days.size
                    ),
                    checked = selectedDays[index],
                    onCheckedChange = { checked -> selectedDays[index] = checked}

                ){
                    Text(label)
                }}


        }


    }
}

