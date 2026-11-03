package com.ruokkis.routes

import com.ruokkis.models.NewRestaurantMenu
import com.ruokkis.models.Restaurant
import com.ruokkis.service.RestaurantService2
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

fun Route.configureRestaurantRoutes(
    restaurantService: RestaurantService2
) {
    route("/restaurants") {
        get {
            val restaurants = restaurantService.getRestaurants()
            call.respond(restaurants)
        }

        post {
            val restaurantData = call.receive<NewRestaurantMenu>()

            if (restaurantData.name.isBlank()) {
                call.respond(HttpStatusCode.BadRequest, "Name cannot be empty")
                return@post
            }

            if (restaurantData.url.isBlank()) {
                call.respond(HttpStatusCode.BadRequest, "Url cannot be empty")
                return@post
            }

            if (
                !restaurantData.url.startsWith("http://") &&
                !restaurantData.url.startsWith("https://") &&
                !restaurantData.url.startsWith("www.")
                ) {
                call.respond(HttpStatusCode.BadRequest, "Not a valid url")
                return@post
            }

            val newRestaurant: Restaurant? = restaurantService.createRestaurant(restaurantData)

            if (newRestaurant == null) {
                call.respond(
                    HttpStatusCode.Conflict,
                    "Restaurant with name '${restaurantData.name}' already exists"
                )
                return@post
            }

            call.respond(HttpStatusCode.Created, newRestaurant)
        }

        // /restaurants/{id} haetaan esim tietyn ravintolan tiedot
        route("/{id}") {

            get() {
                // TODO hae tietyn ravintolan tiedot tietokannasta id:n perusteella
            }

            put() {
                // update voisi olla hyvä, jos haluaa muuttaa jotakin, kenttää esim campus, jos tyhjä,
                // voisi ottaa parametrina kentän nimen jota muutetaan, jos ei löydy badRequest
            }

            delete() {
                // TODO voi poistaa tietyn ravintolan id:n perusteella
            }

            // /restaurants/{id}/menu
            route("/menu") {

                get() {
                    // TODO palauttaa viikkomenun
                }

                // /restaurants/{id}/menu/today
                get("/today") {
                    // TODO palauttaa tämän päivän menun
                }

                // /restaurants/{id}/menu/day/{weekday} esim. ke
                get("/day/{weekday}") {
                    // TODO hakee päivä menun viikonpäivän mukaan
                }

                // /restaurants/{id}/menu/day/{date} esim. 2026-11-05
                get("/day/{date}") {
                    // TODO hakee päivä menun tietyn päiväämäärän mukaan
                }
            }
        }
    }
}