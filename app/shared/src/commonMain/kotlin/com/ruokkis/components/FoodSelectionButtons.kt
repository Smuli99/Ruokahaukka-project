package com.ruokkis.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun FooSelectionButtons(
    selectedOption: String = "",
    onOptionSelected: (String) -> Unit = {}
) {
    SingleChoiceSegmentedButtonRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        SegmentedButton(
            selected = selectedOption == "Kaikki",
            onClick = { onOptionSelected("Kaikki") },
            shape = SegmentedButtonDefaults.itemShape(1, 4)
        ) {
            Text("Kaikki ruoat")
        }

        SegmentedButton(
            selected = selectedOption == "Lemmpparit",
            onClick = { onOptionSelected("Lemmpparit") },
            shape = SegmentedButtonDefaults.itemShape(2, 4)
        ) {
            Text("Lempi Ruoat")
        }
    }
}