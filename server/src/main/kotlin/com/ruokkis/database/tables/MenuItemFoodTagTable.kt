package com.ruokkis.database.tables

import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.Table

object MenuItemFoodTagTable : Table("menu_item_food_tag") {
    val menuItemFoodId = reference(
        "menu_item_food_id",
        MenuItemFoodTable,
        ReferenceOption.CASCADE,
    )
    val allergyId = reference(
        "allergy_id",
        AllergyTable,
        ReferenceOption.CASCADE,
    )

    override val primaryKey = PrimaryKey(menuItemFoodId, allergyId)
}