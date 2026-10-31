package com.ruokkis.repository

import com.ruokkis.database.AppDatabase
import com.ruokkis.models.NewRestaurant
import com.ruokkis.models.Restaurant
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.lowerCase
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import com.ruokkis.database.tables.Restaurant as RestaurantTable

/**
 * Täällä tehdään toteutukset tietokanta kyselyille
 * esim haetaan tietokannasta kaikki ravintolat
 */
class RestaurantRepositoryImpl(
    private val database: AppDatabase
) : RestaurantRepository {

    // toteutus ravintoloiden hakuun tietokannasta
    override suspend fun findAll(): List<Restaurant> =
        database.dbQuery {
            RestaurantTable.selectAll().map { row ->
                Restaurant(
                    id = row[RestaurantTable.id].value.toString(),
                    name = row[RestaurantTable.name],
                    url = row[RestaurantTable.url],
                    campus = row[RestaurantTable.campus],
                )
            }
        }

    // Tarkistetaan löytyykö ravintola jo tietokannasta nimen perusteella, jos löytyy paluttaa true
    override suspend fun findByName(name: String): Boolean =
        database.dbQuery {
            RestaurantTable
                .selectAll()
                .where {
                    RestaurantTable.name.lowerCase() eq name.lowercase()
                }
                .count() > 0
        }

    override suspend fun create(restaurant: NewRestaurant): Restaurant =
        database.dbQuery {
            val result = RestaurantTable.insert {
                it[name] = restaurant.name
                it[url] = restaurant.url
                it[campus] = restaurant.campus
            }

            val id = result.resultedValues
                ?.singleOrNull()
                ?.get(RestaurantTable.id)
                ?.value
                ?: error("Restaurant creation failed")

            Restaurant(
                id = id.toString(),
                name = restaurant.name,
                url = restaurant.url,
                campus = restaurant.campus,
            )
        }

    // toteutus ravintolan tietojen hakuun
}