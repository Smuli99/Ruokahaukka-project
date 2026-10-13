package com.ruokkis.service

import com.ruokkis.model.DayMenu
import com.ruokkis.model.RestaurantMenu
import kotlinx.datetime.LocalDate

/**
 * Rajapinta kaikille ravintoloille.
 *
 * getWeek()    --- hakee koko viikon ruokalistan
 * getDay(date) --- hakee annetun päivän ruokalistan
 * getToday     --- hakee tämän päivän ruokalistan
 */
interface RestaurantService {
    val id: String

    suspend fun getWeek(): RestaurantMenu
    suspend fun getDay(date: LocalDate): DayMenu?
    suspend fun getToday(): DayMenu?
}