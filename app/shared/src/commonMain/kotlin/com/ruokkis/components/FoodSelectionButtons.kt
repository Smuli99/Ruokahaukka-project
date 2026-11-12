package com.ruokkis.components

import androidx.compose.foundation.layout.fillMaxWidth
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
fun FoodSelectionButtons(
    selectedOption: String = "",
    onOptionSelected: (String) -> Unit = {}
) {
    SingleChoiceSegmentedButtonRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        SegmentedButton(
            selected = selectedOption == "all",
            onClick = { onOptionSelected("all") },
            shape = SegmentedButtonDefaults.itemShape(1, 4, RoundedCornerShape(0.dp)),
            colors = SegmentedButtonDefaults.colors(
                activeContainerColor = MaterialTheme.colorScheme.secondary,
                activeContentColor = MaterialTheme.colorScheme.onPrimary,
                inactiveContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                inactiveContentColor = MaterialTheme.colorScheme.onSecondaryContainer
            ),
            icon = {}
            ) {
            Text("Kaikki ruoat")
        }

        SegmentedButton(
            selected = selectedOption == "favourites",
            onClick = { onOptionSelected("favourites") },
            shape = SegmentedButtonDefaults.itemShape(2, 4, RoundedCornerShape(0.dp)),
            colors = SegmentedButtonDefaults.colors(
                activeContainerColor = MaterialTheme.colorScheme.secondary,
                activeContentColor = MaterialTheme.colorScheme.onPrimary,
                inactiveContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                inactiveContentColor = MaterialTheme.colorScheme.onSecondaryContainer
            ),
            icon = {}
        ) {
            Text("Lempi Ruoat")
        }
    }
}