package com.ruokkis.integration

import com.ruokkis.models.NewDayMenu
import com.ruokkis.models.NewFood
import com.ruokkis.models.NewMenuItem
import com.ruokkis.models.NewRestaurantMenu
import com.ruokkis.models.NewWeekMenu
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

// Riisiä esiintyy molempina päivinä, jotta voidaan testata ruoan uudelleenkäyttö
fun buildMenu(weekStart: LocalDate = LocalDate(2026, 11, 2)) = NewRestaurantMenu(
    name = "Ilokivi",
    url = "https://www.semma.fi/ravintolat/seminaarinmaki/ilokivi/",
    campus = "Seminaarinmäki",
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