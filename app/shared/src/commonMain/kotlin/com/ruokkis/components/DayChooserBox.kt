package com.ruokkis.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.* // lisätty

@Composable
@Preview
fun DayChooserBox() {

    var selectedIndex by remember {mutableIntStateOf(0)} //TODO: Ottaa automaattisesti tän päivän indeksin "alku" veikkaukseks
    val days = listOf("All", "Ma", "Ti", "Ke", "To", "Pe", "La", "Su")
    //korvaa All jollain väkäsellä
    //Pitää tehdä logiikka, jolla All -> Deslect otehrs / Select All)

    Box(
        modifier = Modifier
            .padding(10.dp)
    ) {

        SingleChoiceSegmentedButtonRow {
            days.forEachIndexed { index, label ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = days.size
                    ),
                    icon ={},
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = MaterialTheme.colorScheme.secondary,
                        activeContentColor = MaterialTheme.colorScheme.onPrimary,
                        inactiveContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        inactiveContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    ),
                    onClick = { selectedIndex = index },
                    selected = index == selectedIndex, label = { Text(label) }

                )


            }


        }
    }
}

