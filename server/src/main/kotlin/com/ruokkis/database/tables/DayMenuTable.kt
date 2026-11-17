package com.ruokkis.database.tables

import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable
import org.jetbrains.exposed.v1.datetime.date

object DayMenuTable : UUIDTable("day_menu") {
    val weekMenuId = reference(
        "week_menu_id",
        WeekMenuTable,
        ReferenceOption.CASCADE
    )
    val date = date("date")
    val weekday = varchar("weekday", 2)
    val lunchTime = varchar("lunch_time", 20).nullable()

    init {
        uniqueIndex(weekMenuId, date)
    }
}