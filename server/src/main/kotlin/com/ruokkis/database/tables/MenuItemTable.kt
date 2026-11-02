package com.ruokkis.database.tables

import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable

object MenuItemTable : UUIDTable("menu_item") {
    val dayMenuId = reference(
        "day_menu_id",
        MenuItemTable,
        ReferenceOption.CASCADE,
    )

    val foodId = reference("food_id", FoodTable)
    val price = text("price").nullable()
    val info = text("info").nullable()
    val sortOrder = integer("sort_order").default(0)
}