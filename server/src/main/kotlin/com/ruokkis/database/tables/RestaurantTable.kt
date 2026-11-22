package com.ruokkis.database.tables

import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable

object RestaurantTable : UUIDTable("restaurant") {
    val name = varchar("name", 100).uniqueIndex()
    val url = text("url")
    val campus = varchar("campus", 100).nullable()
    val favorite = bool("favorite").default(false)
}