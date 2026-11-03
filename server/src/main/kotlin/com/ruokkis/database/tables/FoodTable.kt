package com.ruokkis.database.tables

import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable

object FoodTable : UUIDTable("food") {
    val name = text("name")
    val normalizedName = text("normalized_name").uniqueIndex()
}