package com.ruokkis.service

import com.ruokkis.models.NewRestaurant
import com.ruokkis.models.Restaurant
import com.ruokkis.repository.RestaurantRepository

/**
 * Luokka kommunikoi Routejen ja Repositoryn välillä.
 * Kun Routessa kutsutaan esim GET("/piato") millä halutaan saada ravintolan tiedot ja viikkolista
 * näkyviin, niin täällä hoidetaan sen logiikka, eli kutsutaan Repositoria, joka hoitaa komminikoinnin
 * tietokannan kanssa ja lähetetään se eteenpäin Routelle
 */
class RestaurantService2(
    private val restaurantRepository: RestaurantRepository
) {
    // Haetaan kaikki ravintolat tietokannasta
    suspend fun getRestaurants(): List<Restaurant> {
        return restaurantRepository.findAll()
    }

//    // Haetaan tietty ravintola
//    suspend fun getRestaurant(id: UUID): Restaurant? {
//        // TODO
//    }
//
    // Luodaan uusi ravintola
    suspend fun createRestaurant(restaurant: NewRestaurant): Restaurant? {
        if (restaurantRepository.findByName(restaurant.name)) return null
        return restaurantRepository.create(restaurant)
    }
}