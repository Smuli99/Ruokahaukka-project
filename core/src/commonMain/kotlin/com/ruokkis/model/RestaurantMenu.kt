package com.ruokkis.model

import kotlinx.serialization.Serializable

/**
 * Data halutussa muodossa
 * tänne voi lisätä uusia kenttiä jne, mitä ei ole semmalta haetussa datassa
 */
@Serializable
data class RestaurantMenu(
    val restaurantName: String? = null,
    val restaurantUrl: String? = null,
    val footer: String? = null,
    val days: List<DayMenu> = emptyList(),
)

@Serializable
data class DayMenu(
    val date: String, // "YYYY-MM-DD"
    val weekday: String, // "ma", "ti", "ke", "to", "pe", "la", "su"
    val lunchTime: String? = null,
    val meals: List<Meal> = emptyList(),
)

@Serializable
data class Meal(
    val name: String? = null, // "Lounas", "From The Table"
    val prices: String? = null,
    val dishes: List<String> = emptyList(),
)