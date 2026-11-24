package com.ruokkis.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.ruokkis.AppTheme

@Composable
@Preview
fun TopBar(
    selectedTheme: AppTheme = AppTheme.SYSTEM,
    onThemeChange: (AppTheme) -> Unit = {}) {

    var expanded by remember { mutableStateOf(false) }

    CenterAlignedTopAppBar(
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),

        title = {
            Text(
                text = "Ruokkis",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                fontSize = 30.sp
            )
        },

        actions = {
            IconButton(
                onClick = { expanded = true}
            ) {
                Icon(
                    imageVector = when (selectedTheme) {
                        AppTheme.LIGHT -> Icons.Outlined.LightMode
                        AppTheme.DARK -> Icons.Outlined.DarkMode
                        AppTheme.SYSTEM -> routine
                    },
                    contentDescription = "Vaihda teema"
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Vaalea teema") },
                    onClick = {
                        onThemeChange(AppTheme.LIGHT)
                        expanded = false
                    },
                    leadingIcon = {
                        Icon(Icons.Outlined.LightMode, null)
                    }
                )

                DropdownMenuItem(
                    text = { Text("Tumma teema") },
                    onClick = {
                        onThemeChange(AppTheme.DARK)
                        expanded = false
                    },
                    leadingIcon = {
                        Icon(Icons.Outlined.DarkMode, null)
                    }
                )

                DropdownMenuItem(
                    text = { Text("Järjestelmän mukainen") },
                    onClick = {
                        onThemeChange(AppTheme.SYSTEM)
                        expanded = false
                    },
                    leadingIcon = {
                        Icon(routine, null)
                    }
                )
            }
        }
    )
}
//    TopAppBar (
//        colors = topAppBarColors(
//            containerColor = MaterialTheme.colorScheme.primaryContainer,
//            titleContentColor = MaterialTheme.colorScheme.primary,
//        ),
//
//        title = {
//            Text(
//                text = "Ruokkis",
//                fontWeight = FontWeight.Bold,
//                fontFamily = FontFamily.Serif,
//            )//Keskitä?
//        }
//    )
