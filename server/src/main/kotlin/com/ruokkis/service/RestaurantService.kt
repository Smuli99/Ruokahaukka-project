package com.ruokkis.service

import com.ruokkis.models.Allergy
import com.ruokkis.models.DayMenu
import com.ruokkis.models.Food
import com.ruokkis.models.NewRestaurantMenu
import com.ruokkis.models.Restaurant
import com.ruokkis.models.WeekMenu
import com.ruokkis.repository.RestaurantRepository
import kotlinx.datetime.LocalDate
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

    suspend fun deleteById(id: UUID): Boolean {
        return restaurantRepository.deleteById(id)
    }

    suspend fun findWeekMenu(restaurantId: String, weekStart: LocalDate): WeekMenu? {
        return restaurantRepository.findWeekMenu(restaurantId, weekStart)
    }

    suspend fun findDayMenu(restaurantId: String, day: LocalDate): DayMenu? {
        return restaurantRepository.findDayMenu(restaurantId, day)
    }

    suspend fun findWeekdayMenu(restaurantId: String, day: String): DayMenu? {
        return restaurantRepository.findWeekdayMenu(restaurantId, day)
    }

    suspend fun findDateMenu(restaurantId: String, day: String): DayMenu? {
        return restaurantRepository.findWeekdayMenu(restaurantId, day)
    }

    suspend fun updateRestaurant(restaurantId: UUID): Restaurant {
        return restaurantRepository.updateRestaurant(restaurantId)
    }

    // Luodaan uusi ravintola
    suspend fun createRestaurant(restaurant: NewRestaurantMenu): Restaurant? {
        if (restaurantRepository.existsByName(restaurant.name)) return null
        return restaurantRepository.create(restaurant)
    }

    suspend fun getFoods(): List<Food> {
        return restaurantRepository.getFoods()
    }

    suspend fun getFoodById(id: UUID): Food? {
        return restaurantRepository.getFoodById(id)
    }

    suspend fun updateFood(id: UUID): Food {
        return restaurantRepository.updateFood(id)
    }

    suspend fun getAllergies(): List<Allergy> {
        return restaurantRepository.getAllergies()
    }
}