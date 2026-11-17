package com.ruokkis.dto

import com.ruokkis.model.weekdayAbbreviation
import com.ruokkis.models.NewDayMenu
import com.ruokkis.models.NewFood
import com.ruokkis.models.NewMenuItem
import com.ruokkis.models.NewRestaurantMenu
import com.ruokkis.models.NewWeekMenu
import kotlinx.datetime.LocalDate

/**
 * Muunnetaan data halutulla tavalla tietokantaa varten
 * Muuntaa siis datan, jonka RestaurantApiDto parsii
 *
 * tönne lisättävä weekMenu, dayMenu jne...
 */
fun RestaurantDto.toRestaurant() = NewRestaurantMenu(
    name = name.substringAfter(" "),
    url = url,
    campus = whatCampus(this.name),
    weekMenu = NewWeekMenu(
        weekStart = LocalDate.parse(
            days?.firstOrNull()?.date?.take(10)
                ?: error("Day fields are missing")
        ),

        days = days.mapNotNull { it?.toDayMenu() }
    )
)

private fun DayMenuDto.toDayMenu(): NewDayMenu {
    // "2026-10-14T00:00:00+00:00" -> "2026-10-14" -> LocalDate
    val localDate = LocalDate.parse(
        date?.take(10)
            ?: error("Date field is missing")
    )

    return NewDayMenu(
        date = localDate,
        weekday = localDate.weekdayAbbreviation(),
        lunchTime = lunchTime,
        items = items.mapNotNull { it?.toMenuItem() }
            .filter { it.foods.isNotEmpty() }
            .distinctBy { it.info to it.foods }
    )
}

private fun MenuItemDto.toMenuItem(): NewMenuItem {
    return NewMenuItem(
        price = price,
        info = info,
        sortOrder = sortOrder ?: 0,
        foods = foods.mapNotNull { it?.toFoodItem() }
    )
}

private fun String.toFoodItem(): NewFood {
    var firstParenthesis = indexOf('(')
    var lastParenthesis = indexOf(')')

    if (firstParenthesis == -1 || lastParenthesis == -1) {
        return NewFood(
            name = trim(),
            allergies = emptyList()
        )
    }

    val name = substring(0, firstParenthesis).trim()

    firstParenthesis = lastIndexOf('(')
    lastParenthesis = lastIndexOf(')')

    val allergies = substring(firstParenthesis + 1, lastParenthesis)
        .split(",")
        .map { it.trim() }
        .filter { it.isNotBlank() }

    return NewFood(
        name = name,
        allergies = allergies
    )
}

// Semmat täällä hetkellä
private fun whatCampus(name: String): String? =
    when (name) {
        "Ravintola Piato"      -> "Mattilanniemi"
        "Ravintola Lozzi"      -> "Seminaarinmäki"
        "Ravintola Ilokivi"    -> "Seminaarinmäki"
        "Ravintola Tilia"      -> "Seminaarinmäki"
        "Ravintola Beldeverde" -> "Seminaarinmäki"
        "Ravintola Syke"       -> "Seminaarinmäki"
        "Ravintola Uno"        -> "Ruusupuisto"
        "Ravintola Ylistö"     -> "Ylistönrinne"
        "Ravintola Kvarkki"    -> "Ylistönrinne"
        else -> null
    }