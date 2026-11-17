package com.ruokkis.database.tables

import org.jetbrains.exposed.v1.datetime.date
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable

object WeekMenuTable : UUIDTable("week_menu") {
    val restaurantId = reference(
        "restaurant_id",
        RestaurantTable,
        ReferenceOption.CASCADE
    )
    val weekStart = date("week_start")

    init {
        uniqueIndex(restaurantId, weekStart)
    }
}