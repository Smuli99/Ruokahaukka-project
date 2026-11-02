package com.ruokkis.database.tables

import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.Table

object FoodAllergyTable : Table("food_allergy") {
    val foodId = reference(
        "food_id",
        FoodTable,
        ReferenceOption.CASCADE,
    )

    val allergyId = reference(
        "allergy_id",
        AllergyTable,
        ReferenceOption.CASCADE,
    )

    override val primaryKey = PrimaryKey(foodId, allergyId)
}