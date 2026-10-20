package com.ruokkis.service

import com.ruokkis.model.DayMenu
import com.ruokkis.model.Meal
import com.ruokkis.model.RestaurantMenu
import com.ruokkis.model.weekdayAbbreviation
import kotlinx.datetime.LocalDate

/**
 * Muunnetaan data luettavammaksi,
 * JSON data joka tulee RestaurantMenuDto:sta muutetaan muotoon, jossa päivämäärä ja viikonpäivä
 * esiintyvät luettavammassa muodossa ja muunnetaan se RestaurantMenu malliin
 */
fun RestaurantMenuDto.toRestaurantMenu() = RestaurantMenu(
    restaurantName = restaurantName,
    restaurantUrl = restaurantUrl,
    footer = footer,
    days = days.mapNotNull { it.toDayMenu() }
)

private fun DayMenuDto.toDayMenu(): DayMenu? {
    // "2026-10-14T00:00:00+00:00" -> "2026-10-14" -> LocalDate
    val localDate = date
        ?.substringBefore("T")
        ?.let { runCatching { LocalDate.parse(it) }.getOrNull() }
        ?: return null   // jos päivämäärä puuttuu tai on virheellinen, päivä ohitetaan

    return DayMenu(
        date = localDate.toString(),
        weekday = localDate.weekdayAbbreviation(),
        lunchTime = lunchTime,
        meals = menu.map { Meal(it.name, it.price, it.dishes) }
    )
}