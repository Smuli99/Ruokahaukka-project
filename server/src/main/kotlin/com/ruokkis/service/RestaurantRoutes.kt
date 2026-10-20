package com.ruokkis.service

import com.ruokkis.model.DayMenu
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.RoutingContext
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import kotlinx.datetime.LocalDate

/**
 * Määritellään ravintoiloiden HTTP-reitit
 * Tähän tiedostoon voi tehdään muutoksia, jos on tarvetta saada uudenlaista dataa ravintoloista
 * esim. pelkät päivän ruuat jne.
 *
 * Tähän tiedostoon ei tarvitse tehdä muutoksia uutta ravintoloa lisätessä
 *
 * Reitit:
 * - GET /restaurant            --- lista ravintoloista
 * - GET /{restaurant}          --- koko viikon ruokalista
 * - GET /{restaurant}/today    --- tämän päivän ruokalista
 * - GET /{restaurant/{date}    --- annetun päivän ruokalista
 */
fun Route.restaurantRoutes(services: Map<String, RestaurantService>) {

    fun RoutingContext.service(): RestaurantService {
        val id = call.parameters["restaurant"]
        return services[id] ?: throw RestaurantNotFoundException(id)
    }

    get("/restaurant") {
        call.respond(services.keys.toList())
    }

    route("/{restaurant}") {
        get { call.respond(service().getWeek()) }

        get("/today") {
            call.respondDay(service().getToday())
        }

        get("/{date}") {
            // Tarkistetaan, että päivämäärä on oikeaa muotoa eli yyyy-mm-dd, jos ei niin vastataa 400 bad request
            val date = try {
                LocalDate.parse(call.parameters["date"].orEmpty())
            } catch (e: IllegalArgumentException) {
                return@get call.respondText(
                    "Käytä muotoa YYYY-MM-DD",
                    status = HttpStatusCode.BadRequest
                )
            }

            call.respondDay(service().getDay(date))
        }
    }
}

/**
 * Apufunktio, joka vastaa päivän ruokalistan jos löytyy,
 * jos null niin vastaa 204 no content
 */
private suspend fun ApplicationCall.respondDay(day: DayMenu?) {
    if (day != null) {
        respond(day)
    } else {
        respond(HttpStatusCode.NoContent)
    }
}