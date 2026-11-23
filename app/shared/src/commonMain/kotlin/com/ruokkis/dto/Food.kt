package com.ruokkis.dto

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable


@Serializable
data class DayMenu(
    val id: String,
    val date: LocalDate,
    val weekday: String,
    val lunchTime: String?,
    val items: List<MenuItem> = emptyList(),
    val weekMenuId: String //lisätty, että JOIN toimii
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
    val favorite: Boolean,
)
