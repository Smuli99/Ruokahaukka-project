package com.ruokkis.service

import com.ruokkis.model.DayMenu
import com.ruokkis.model.RestaurantMenu
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

/**
 * Hakee ravintolan ruokalistan HTTP:llä annetusta osoitteesta ja tarjoaa sen RestaurantMenu muodossa
 * Sama luokka toimii kaikissa ravintoloissa
 *
 * @param id ravintolan tunniste esim. piato
 * @param url osoite, josta data haetaan
 * @param client HTTP-client
 * @param today hakee tämän päivän automaattisesti
 */
class HttpRestaurantService (
    override val id: String,
    private val url: String,
    private val client: HttpClient,
    private val today: () -> LocalDate = { Clock.System.todayIn(TimeZone.of("Europe/Helsinki")) }
) : RestaurantService {

    // Hakee ravintolan ruokalistan osoitteesta
    private suspend fun fetchMenu(): RestaurantMenu =
        try {
            client.get(url).body<RestaurantMenuDto>().toRestaurantMenu()
        } catch (e: Exception) {
            throw UpstreamException(e)
        }

    // käytetään kun haetaan koko viikon ruokalista
    override suspend fun getWeek(): RestaurantMenu = fetchMenu()

    // käytetään kun haetaan tietyn päivän ruokalista muodossa yyyy-mm-dd, voi olla null
    override suspend fun getDay(date: LocalDate): DayMenu? =
        fetchMenu().days.firstOrNull { it.date == date.toString() }

    // käytetään hakemaan tämän päivän ruokalistan, voi olla null
    override suspend fun getToday(): DayMenu? = getDay(today())
}
