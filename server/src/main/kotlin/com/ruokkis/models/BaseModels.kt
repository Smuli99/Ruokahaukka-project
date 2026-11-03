package com.ruokkis.models

import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDate

@Serializable
data class Restaurant(
    val id: String,
    val name: String,
    val url: String,
    val campus: String?
)

// Vastaus endpointille get rafla
@Serializable
data class RestaurantDetails(
    val restaurant: Restaurant,
    val weekMenu: WeekMenu?,
)

@Serializable
data class WeekMenu(
    val id: String,
    val weekStart: LocalDate,
    val days: List<DayMenu> = emptyList(),
)

@Serializable
data class DayMenu(
    val id: String,
    val date: LocalDate,
    val weekday: String,
    val lunchTime: String?,
    val items: List<MenuItem> = emptyList()
)

@Serializable
data class MenuItem(
    val id: String,
    val price: String?,
    val info: String?,
    val sortOrder: Int,
    val foods: List<MenuItemFood> = emptyList(),
)

@Serializable
data class MenuItemFood(
    val id: String,
    val food: Food,
    val sortOrder: Int,
    val allegies: List<Allergy> = emptyList(),
)

@Serializable
data class Food(
    val id: String,
    val name: String,
    val normalizedName: String,
)

@Serializable
data class Allergy(
    val id: String,
    val tag: String,
    val nameFi: String
)

data class SyncResult(val restaurant: Restaurant, val weekCreated: Boolean)