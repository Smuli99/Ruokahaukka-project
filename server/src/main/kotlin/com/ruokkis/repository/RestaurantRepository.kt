package com.ruokkis.repository

import com.ruokkis.models.NewRestaurantMenu
import com.ruokkis.models.NewWeekMenu
import com.ruokkis.models.Restaurant
import com.ruokkis.models.WeekMenu
import kotlinx.datetime.LocalDate
import java.util.UUID

/**
 * Toiminnot, mitä halutaan ravintoloille
 * Täällä siis määritellään asiat mitä halutaan tehdä tietokannassa
 * esim. lisätään uusi ravintola, haetaan kaikki ravintolat, haetaan ravintolan viikkolista jne.
 */
interface RestaurantRepository {
    suspend fun findAll(): List<Restaurant>
    suspend fun create(restaurant: NewRestaurantMenu): Restaurant
    suspend fun existsByName(name: String): Boolean
    suspend fun findByName(name: String): Restaurant?
    suspend fun findWeekMenu(restaurantId: String, weekStart: LocalDate): WeekMenu?
    suspend fun createWeekMenu(restaurantId: String, menu: NewWeekMenu): String

    //suspend fun findRestaurantInfo(restaurant: )
     suspend fun findById(id: UUID): Restaurant?
//    suspend fun update(restaurant: Restaurant): Restaurant
//    suspend fun delete(restaurant: Restaurant): Boolean
}