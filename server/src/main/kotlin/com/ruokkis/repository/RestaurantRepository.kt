package com.ruokkis.repository

import com.ruokkis.models.NewRestaurant
import com.ruokkis.models.Restaurant

/**
 * Toiminnot, mitä halutaan ravintoloille
 * Täällä siis määritellään asiat mitä halutaan tehdä tietokannassa
 * esim. lisätään uusi ravintola, haetaan kaikki ravintolat, haetaan ravintolan viikkolista jne.
 */
interface RestaurantRepository {
    suspend fun findAll(): List<Restaurant>
    suspend fun create(restaurant: NewRestaurant): Restaurant
    suspend fun findByName(name: String): Boolean
//    suspend fun findByName(name: String): Restaurant?
//    suspend fun findById(id: String): Restaurant?
//    suspend fun update(restaurant: Restaurant): Restaurant
//    suspend fun delete(restaurant: Restaurant): Boolean
}