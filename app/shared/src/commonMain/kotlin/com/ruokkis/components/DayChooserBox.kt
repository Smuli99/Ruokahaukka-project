package com.ruokkis.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun DayChooserBox(
    selectedDay: Int = 1,
    onDaySelected: (Int) -> Unit = {}
) {

    val days = listOf("All", "Ma", "Ti", "Ke", "To", "Pe", "La", "Su")
    //korvaa All jollain väkäsellä
    //Pitää tehdä logiikka, jolla All -> Deslect otehrs / Select All)
    // All -> kaikki tummanvihreänä?

    Box(
        modifier = Modifier
            .padding(10.dp)
    ) {

        SingleChoiceSegmentedButtonRow {
            days.forEachIndexed { index, label ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = days.size,
                        baseShape = RoundedCornerShape(0.dp)
                    ),
                    icon ={},
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = MaterialTheme.colorScheme.secondary,
                        activeContentColor = MaterialTheme.colorScheme.onPrimary,
                        inactiveContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        inactiveContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    ),
                    onClick = {
                        onDaySelected(if (index == 0) -1 else index)
                    },
                    selected = selectedDay == -1 || index == selectedDay,
                    label = { Text(label) }
                )
            }
        }
    }
}

