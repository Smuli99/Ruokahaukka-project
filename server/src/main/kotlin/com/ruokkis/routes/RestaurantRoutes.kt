package com.ruokkis.routes

import com.ruokkis.models.NewRestaurant
import com.ruokkis.models.Restaurant
import com.ruokkis.service.RestaurantService2
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Route.restaurantRoutes2(
    restaurantService: RestaurantService2
) {
    route("/restaurants") {
        get {
            val restaurants = restaurantService.getRestaurants()
            call.respond(restaurants)
        }

        post {
            val restaurantData = call.receive<NewRestaurant>()

            if (restaurantData.name.isBlank()) {
                call.respond(HttpStatusCode.BadRequest, "Name cannot be empty")
                return@post
            }

            if (restaurantData.url.isBlank()) {
                call.respond(HttpStatusCode.BadRequest, "Url cannot be empty")
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
                    "Restaurant already exists"
                )
                return@post
            }

            call.respond(HttpStatusCode.Created, newRestaurant)
        }
    }
}