package com.ruokkis.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * Parsitaan halutut kentät semma/campus datasta
 */
@Serializable
data class RestaurantDto(
    @SerialName("RestaurantName")
    val name: String,

    @SerialName("RestaurantUrl")
    val url: String,

    @SerialName("MenusForDays")
    val days: List<DayMenuDto?>,
)

@Serializable
data class DayMenuDto(
    @SerialName("Date")
    val date: String?,

    @SerialName("LunchTime")
    val lunchTime: String?,

    @SerialName("SetMenus")
    val items: List<MenuItemDto?>,
)

@Serializable
data class MenuItemDto(
    @SerialName("Name")
    val info: String?, // Lounas, From the Table

    @SerialName("Price")
    val price: String?,

    @SerialName("SortOrder")
    val sortOrder: Int?,

    @SerialName("Components")
    val foods: List<String?>,
)