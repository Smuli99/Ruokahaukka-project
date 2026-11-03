package com.ruokkis.client

import com.ruokkis.config.RestaurantSource
import com.ruokkis.dto.RestaurantDto
import com.ruokkis.dto.toRestaurant
import com.ruokkis.models.NewRestaurantMenu
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class RestaurantFetcher(
    private val client: HttpClient
) {

    suspend fun fetch(source: RestaurantSource): NewRestaurantMenu =
        client.get(source.url).body<RestaurantDto>().toRestaurant()
}