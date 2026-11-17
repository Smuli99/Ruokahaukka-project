package com.ruokkis.repository

import com.ruokkis.database.AppDatabase
import com.ruokkis.database.tables.AllergyTable
import com.ruokkis.database.tables.DayMenuTable
import com.ruokkis.database.tables.FoodTable
import com.ruokkis.database.tables.MenuItemFoodTable
import com.ruokkis.database.tables.MenuItemFoodTagTable
import com.ruokkis.database.tables.MenuItemTable
import com.ruokkis.database.tables.RestaurantTable
import com.ruokkis.database.tables.WeekMenuTable
import com.ruokkis.models.NewRestaurantMenu
import com.ruokkis.models.NewWeekMenu
import com.ruokkis.models.Restaurant
import com.ruokkis.models.WeekMenu
import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.lowerCase
import org.jetbrains.exposed.v1.datetime.date
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.insertAndGetId
import org.jetbrains.exposed.v1.jdbc.select
import org.jetbrains.exposed.v1.jdbc.selectAll
import java.util.UUID

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
    override suspend fun existsByName(name: String): Boolean =
        database.dbQuery {
            RestaurantTable
                .selectAll()
                .where {
                    RestaurantTable.name.lowerCase() eq name.lowercase()
                }
                .count() > 0
        }

    // Etsitään ravintola nimen mukaan
    override suspend fun findByName(name: String): Restaurant? {
        return database.dbQuery {
            RestaurantTable
                .selectAll()
                .where {
                    RestaurantTable.name.lowerCase() eq name.lowercase()
                }
                .singleOrNull()
                ?.toRestaurant()
        }
    }

    override suspend fun create(restaurant: NewRestaurantMenu): Restaurant =
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

    override suspend fun createWeekMenu(restaurantId: String, menu: NewWeekMenu): String =
        database.dbQuery {
            val restaurantUuid = UUID.fromString(restaurantId)

            // Poistetaan vanha viikkomenu (jos olemassa)
            WeekMenuTable.deleteWhere { WeekMenuTable.restaurantId eq restaurantUuid }

            val weekId = WeekMenuTable.insertAndGetId {
                it[WeekMenuTable.restaurantId] = restaurantUuid
                it[WeekMenuTable.weekStart] = menu.weekStart
            }

            menu.days.forEach { day ->
                val dayId = DayMenuTable.insertAndGetId {
                    it[DayMenuTable.weekMenuId] = weekId
                    it[DayMenuTable.date] = day.date
                    it[DayMenuTable.weekday] = day.weekday
                    it[DayMenuTable.lunchTime] = day.lunchTime
                }

                day.items.forEach { item ->
                    val itemId = MenuItemTable.insertAndGetId {
                        it[MenuItemTable.dayMenuId] = dayId
                        it[MenuItemTable.price] = item.price
                        it[MenuItemTable.info] = item.info
                        it[MenuItemTable.sortOrder] = item.sortOrder
                    }

                    item.foods.forEachIndexed { index, food ->
                        val itemFoodId = MenuItemFoodTable.insertAndGetId {
                            it[MenuItemFoodTable.menuItemId] = itemId
                            it[MenuItemFoodTable.foodId] = findOrCreateFood(food.name)
                            it[MenuItemFoodTable.sortOrder] = index
                        }

                        food.allergies.distinct().forEach { tag ->
                            MenuItemFoodTagTable.insert {
                                it[MenuItemFoodTagTable.menuItemFoodId] = itemFoodId
                                it[MenuItemFoodTagTable.allergyId] = findOrCreateAllergy(tag)
                            }
                        }
                    }
                }
            }

            weekId.value.toString()
        }

    override suspend fun findWeekMenu(restaurantId: String, weekStart: LocalDate): WeekMenu? {
        return database.dbQuery {
            WeekMenuTable
                .selectAll()
                .where {
                    (WeekMenuTable.restaurantId eq UUID.fromString(restaurantId)) and
                    (WeekMenuTable.weekStart eq weekStart)
                }
                .singleOrNull()
                ?.toWeekMenu()
        }
    }

    private fun findOrCreateFood(name: String): UUID {
        val normalized = name.normalizeFoodName()
        val existing = FoodTable.selectAll()
            .where { FoodTable.normalizedName eq normalized }
            .singleOrNull()
        if (existing != null) return existing[FoodTable.id].value

        return FoodTable.insertAndGetId {
            it[FoodTable.name] = name
            it[FoodTable.normalizedName] = normalized
        }.value
    }

    private fun findOrCreateAllergy(tag: String): UUID {
        val existing = AllergyTable.selectAll()
            .where { AllergyTable.tag eq tag }
            .singleOrNull()
        if (existing != null) return existing[AllergyTable.id].value

        return AllergyTable.insertAndGetId {
            it[AllergyTable.tag] = tag
            // voisi lisätä nameFi:n omassa funktiossa
        }.value
    }

    // muunnetaan db result Restaurant olioksi
    private fun ResultRow.toRestaurant() = Restaurant(
        id = this[RestaurantTable.id].toString(),
        name = this[RestaurantTable.name],
        url = this[RestaurantTable.url],
        campus = this[RestaurantTable.campus],
    )

    // muunnetaan db result WeekMenu olioksi
    private fun ResultRow.toWeekMenu() = WeekMenu(
        id = this[WeekMenuTable.id].toString(),
        weekStart = this[WeekMenuTable.weekStart]
    )

    fun String.normalizeFoodName(): String =
        trim().lowercase().replace(Regex("\\s+"), " ")
}