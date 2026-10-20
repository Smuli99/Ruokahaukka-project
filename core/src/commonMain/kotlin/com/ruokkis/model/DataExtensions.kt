package com.ruokkis.model

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

// Muuntaa päivät muotoon "ma", "ti" jne
fun LocalDate.weekdayAbbreviation(): String = when (dayOfWeek) {
    DayOfWeek.MONDAY -> "ma"
    DayOfWeek.TUESDAY -> "ti"
    DayOfWeek.WEDNESDAY -> "ke"
    DayOfWeek.THURSDAY -> "to"
    DayOfWeek.FRIDAY -> "pe"
    DayOfWeek.SATURDAY -> "la"
    DayOfWeek.SUNDAY -> "su"
    else -> error("Unknown day of week: $dayOfWeek")
}