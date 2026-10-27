package com.ruokkis

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.minus
import kotlinx.datetime.plus

// Testi data Json rakenteella
object TestData {

    // Haetaan päivä
    fun today(): LocalDate =
        Clock.System.todayIn(TimeZone.of("Europe/Helsinki"))

    // Luodaan päivä menu data annetulle päivälle ja ruualle
    private fun day(date: LocalDate, food: String) = """
        {
          "Date": "${date}T00:00:00+00:00",
          "LunchTime": "10.30-14.00",
          "SetMenus": [
            {
              "SortOrder": 1,
              "Name": "Lounas",
              "Price": "2,95 € / 5,90 € / 9,20 €",
              "Components": [ "$food" ]
            }
          ]
        }
    """.trimIndent()

    // Data jota testit käyttävät
    val dataJson = """
        {
          "RestaurantName": "Ravintola Piato",
          "RestaurantUrl": "https://www.semma.fi/ravintolat/mattilanniemi/ravintola-piato/",
          "PriceHeader": null,
          "Footer": "Keittolounas klo 10.30-14.00",
          "MenusForDays": [
            ${day(today().minus(1, DateTimeUnit.DAY), "ruoka eilen")},
            ${day(today(), "ruoka tanaan")},
            ${day(today().plus(1, DateTimeUnit.DAY), "ruoka huomenna")}
          ],
          "ErrorText": null
        }
    """.trimIndent()
}