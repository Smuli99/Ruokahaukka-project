package com.ruokkis.models

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class NewRestaurantMenu(
    val name: String,
    val url: String,
    val campus: String?,
    val weekMenu: NewWeekMenu
)

@Serializable
data class NewWeekMenu(
    val weekStart: LocalDate,
    val days: List<NewDayMenu> = emptyList()
)

@Serializable
data class NewDayMenu(
    val date: LocalDate,
    val weekday: String,
    val lunchTime: String?,
    val items: List<NewMenuItem> = emptyList()
)

@Serializable
data class NewMenuItem(
    val price: String?,
    val info: String?,
    val sortOrder: Int,
    val foods: List<NewFood> = emptyList(),
)

@Serializable
data class NewFood(
    val name: String,
    val allergies: List<String> = emptyList(),
)