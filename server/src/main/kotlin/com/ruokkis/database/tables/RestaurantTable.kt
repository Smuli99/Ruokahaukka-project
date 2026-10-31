package com.ruokkis.database.tables

import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable

object Restaurant : UUIDTable("restaurant") {
    val name = varchar("name", 50).uniqueIndex()
    val url = text("url")
    val campus = varchar("campus", 50).nullable()
}