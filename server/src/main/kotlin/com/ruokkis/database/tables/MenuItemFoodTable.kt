package com.ruokkis.database.tables

import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable

object MenuItemFoodTable : UUIDTable("menu_item_food") {
    val menuItemId = reference(
        "menu_item_id",
        MenuItemTable,
        ReferenceOption.CASCADE,
    )
    val foodId = reference("food_id", FoodTable)
    val sortOrder = integer("sort_order").default(0)

    init {
        uniqueIndex(menuItemId, sortOrder)
    }
}