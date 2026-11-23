package com.ruokkis

import com.ruokkis.model.NewDayMenu
import com.ruokkis.model.NewFood
import com.ruokkis.model.NewMenuItem
import com.ruokkis.model.NewRestaurantMenu
import com.ruokkis.model.NewWeekMenu
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

// Riisiä esiintyy molempina päivinä, jotta voidaan testata ruoan uudelleenkäyttö
fun buildMenu(
    name: String = "Ilokivi", url: String = "https://example.com", campus: String? = "Seminaarinmäki",
    weekStart: LocalDate = LocalDate(2026, 11, 2)
) = NewRestaurantMenu(
    name = name,
    url = url,
    campus = campus,
    weekMenu = NewWeekMenu(
        weekStart = weekStart,
        days = listOf(
            NewDayMenu(
                date = weekStart,
                weekday = "ma",
                lunchTime = "10:45–16:00",
                items = listOf(
                    NewMenuItem(
                        price = null, info = "Lounas", sortOrder = 5,
                        foods = listOf(
                            NewFood("Butter Chicken", listOf("L", "G")),
                            NewFood("Riisiä", listOf("G", "Veg")),
                        )
                    )
                )
            ),
            NewDayMenu(
                date = weekStart.plus(1, DateTimeUnit.DAY),
                weekday = "ti",
                lunchTime = "10:45–16:00",
                items = listOf(
                    NewMenuItem(
                        price = null, info = "Lounas", sortOrder = 5,
                        foods = listOf(
                            NewFood("Riisiä", listOf("G", "Veg")),
                            NewFood("Kanakeitto", listOf("L")),
                        )
                    )
                )
            ),
        )
    )
)