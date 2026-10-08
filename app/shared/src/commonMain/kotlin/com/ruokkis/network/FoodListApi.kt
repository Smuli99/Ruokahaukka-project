package com.ruokkis.network

import com.ruokkis.model.PiatoResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class FoodListApi(private val client: HttpClient) {

    suspend fun getRestaurant(): PiatoResponse {
        return client.get("/piato").body()
    }
}