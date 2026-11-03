package com.ruokkis.database.tables

import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable

object AllergyTable : UUIDTable("allergy") {
    val tag = varchar("tag", 20).uniqueIndex()
    val nameFi = varchar("name", 100).nullable()
}