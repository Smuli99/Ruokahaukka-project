package com.ruokkis.network

import com.ruokkis.model.Allergy
import com.ruokkis.model.Restaurant
import com.ruokkis.model.Food
import com.ruokkis.model.RestaurantDetails
import com.ruokkis.model.DayMenu
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.http.HttpStatusCode

class FoodListApi(private val client: HttpClient) {

    // Koko viikon ruokalista id:nä annettava ravintolan nimi esim. "piato"
    suspend fun getWeek(restaurantId: String): RestaurantDetails =
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

    suspend fun updateRestaurant(id: String): Restaurant {
        val response = client.put("/restaurants/$id")
        if (response.status != HttpStatusCode.OK) {
            throw Error("Could not update restaurant $id")
        }
        return response.body()
    }
    suspend fun getRestaurants(): List<Restaurant> {
        val response = client.get("/restaurants")
        return response.body()
    }

    suspend fun getFoods(): List<Food> {
        return client.get("/foods").body()
    }

    suspend fun getFood(id: String): Food? {
        val response = client.get("/food/$id")
        if (response.status == HttpStatusCode.BadRequest) return null
        return response.body()
    }

    suspend fun updateFood(id: String): Food {
        val response = client.put("/food/$id")
        if (response.status != HttpStatusCode.OK) {
            throw Error("Could not update food $id")
        }
        return response.body()
    }

    suspend fun getAllergies(): List<Allergy> {
        println("Fetching allergies...")
        val allergies = client.get("/allergies")
        println("RESPONSE: $allergies")
        return allergies.body()
    }
}


