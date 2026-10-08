package com.ruokkis.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PiatoResponse(
    @SerialName("RestaurantName") val restaurantName: String,
    @SerialName("RestaurantUrl") val restaurantUrl: String,
    @SerialName("Footer") val footer: String,
    @SerialName("MenusForDays") val days: List<DayMenu> = emptyList(),
    @SerialName("ErrorText") val errorText: String? = null,
)

@Serializable
data class DayMenu(
    @SerialName("Date") val date: String,
    @SerialName("LunchTime") val lunchTime: String? = null,
    @SerialName("SetMenus") val menu: List<Meal> = emptyList(),
)

@Serializable
data class Meal(
    @SerialName("Name") val name: String,
    @SerialName("Price") val price: String,
    @SerialName("Components") val dishes: List<String> = emptyList()
)