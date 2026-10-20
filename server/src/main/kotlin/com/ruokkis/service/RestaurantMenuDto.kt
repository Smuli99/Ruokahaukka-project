package com.ruokkis.service

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Parsii halutut kentät semman datasta
 */
@Serializable
data class RestaurantMenuDto(
    @SerialName("RestaurantName") val restaurantName: String? = null,
    @SerialName("RestaurantUrl") val restaurantUrl: String? = null,
    @SerialName("Footer") val footer: String? = null,
    @SerialName("MenusForDays") val days: List<DayMenuDto> = emptyList(),
)

@Serializable
data class DayMenuDto(
    @SerialName("Date") val date: String? = null,
    @SerialName("LunchTime") val lunchTime: String? = null,
    @SerialName("SetMenus") val menu: List<MealDto> = emptyList(),
)

@Serializable
data class MealDto(
    @SerialName("Name") val name: String? = null,
    @SerialName("Price") val price: String? = null,
    @SerialName("Components") val dishes: List<String> = emptyList(),
)