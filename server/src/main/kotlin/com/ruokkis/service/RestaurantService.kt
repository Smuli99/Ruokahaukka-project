package com.ruokkis.service

import com.ruokkis.models.NewRestaurantMenu
import com.ruokkis.models.Restaurant
import com.ruokkis.repository.RestaurantRepository
import java.util.UUID

/**
 * Luokka kommunikoi Routejen ja Repositoryn välillä.
 * Kun Routessa kutsutaan esim GET("/piato") millä halutaan saada ravintolan tiedot ja viikkolista
 * näkyviin, niin täällä hoidetaan sen logiikka, eli kutsutaan Repositoria, joka hoitaa komminikoinnin
 * tietokannan kanssa ja lähetetään se eteenpäin Routelle
 */
class RestaurantService(
    private val restaurantRepository: RestaurantRepository
) {
    // Haetaan kaikki ravintolat tietokannasta
    suspend fun getRestaurants(): List<Restaurant> {
        return restaurantRepository.findAll()
    }


    suspend fun findById(id: UUID): Restaurant? {
        return restaurantRepository.findById(id)
    }

    // Luodaan uusi ravintola
    suspend fun createRestaurant(restaurant: NewRestaurantMenu): Restaurant? {
        if (restaurantRepository.existsByName(restaurant.name)) return null
        return restaurantRepository.create(restaurant)
    }
}