package com.ruokkis.service

import com.ruokkis.client.RestaurantFetcher
import com.ruokkis.config.RestaurantSource
import com.ruokkis.models.NewRestaurantMenu
import com.ruokkis.models.Restaurant
import com.ruokkis.models.SyncResult
import com.ruokkis.repository.RestaurantRepository
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Hoitaa logiikan, verkosta haettavan datan lisäykselle
 */
class RestaurantSyncService(
    private val fetcher: RestaurantFetcher,
    private val repository: RestaurantRepository
) {

    // Synkkaukset ajetaan yksi kerrallaan, jotta kaksi samanaikaista pyyntöä ei luo samaa dataa kahdesti
    private val syncMutex = Mutex()
    suspend fun syncRestaurantMenu(source: RestaurantSource): SyncResult {
        // Haetaan data
        val menu = fetcher.fetch(source)

        return syncMutex.withLock {
            val restaurant: Restaurant =
                repository.findByName(menu.name) ?: repository.create(menu)

            // Viikko on jo synkattu, ei tehdä mitään
            if (repository.findWeekMenu(restaurant.id, menu.weekMenu.weekStart) != null) {
                return@withLock SyncResult(restaurant, weekCreated = false)
            }

            // luodaan uusi viikko menu
            repository.createWeekMenu(restaurant.id, menu.weekMenu)
            SyncResult(restaurant, weekCreated = true)
        }
    }
}