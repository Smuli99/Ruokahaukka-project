package com.ruokkis.network

import com.ruokkis.model.DayMenu
import com.ruokkis.model.RestaurantMenu
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode

class FoodListApi(private val client: HttpClient) {

    // Koko viikon ruokalista id:nä annettava ravintolan nimi esim. "piato"
    suspend fun getWeek(restaurantId: String): RestaurantMenu =
        client.get("/${restaurantId}").body()

    // Tämän päivän ruokalista
    suspend fun getToday(restaurantId: String): DayMenu? {
        val response = client.get("/$restaurantId/today")
        if (response.status == HttpStatusCode.NoContent) return null
        return response.body()
    }

    // Tietyn päivän ruokalista, annettava muodossa yyyy-mm-dd
    suspend fun getDay(restaurantId: String, date: String): DayMenu? {
        val response = client.get("/$restaurantId/$date")
        if (response.status == HttpStatusCode.NoContent) return null
        return response.body()
    }
}