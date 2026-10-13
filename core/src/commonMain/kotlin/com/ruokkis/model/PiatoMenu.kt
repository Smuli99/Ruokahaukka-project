package com.ruokkis.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PiatoResponse(
    @SerialName("RestaurantName") val restaurantName: String? = null,
    @SerialName("RestaurantUrl") val restaurantUrl: String? = null,
    @SerialName("Footer") val footer: String? = null,
    @SerialName("MenusForDays") val days: List<DayMenu> = emptyList(),
    @SerialName("ErrorText") val errorText: String? = null,
)

@Serializable
data class DayMenu(
    @SerialName("Date") val date: String? = null,
    @SerialName("LunchTime") val lunchTime: String? = null,
    @SerialName("SetMenus") val menu: List<Meal> = emptyList(),
)

@Serializable
data class Meal(
    @SerialName("Name") val name: String? = null,
    @SerialName("Price") val price: String? = null,
    @SerialName("Components") val dishes: List<String> = emptyList()
)