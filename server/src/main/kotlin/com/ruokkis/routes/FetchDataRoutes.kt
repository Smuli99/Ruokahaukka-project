package com.ruokkis.routes

import com.ruokkis.config.RestaurantDataSources
import com.ruokkis.config.RestaurantSource
import com.ruokkis.service.RestaurantSyncService
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Route.configureFetchingRoutes(service: RestaurantSyncService) {

    route("/fetch-data") {
        // info message
        get {
            val infoText =
                "Add restaurant name in lowercase to fetch weekly menu data, e.g. /fetch-data/piato"
            call.respondText(infoText)
        }

        // Haetaan data semmalta / cateringilta
        post("/{restaurant}") {
            val name = call.parameters["restaurant"]
                ?: return@post call.respond(HttpStatusCode.BadRequest, "Not a valid parameter")

            val source: RestaurantSource = RestaurantDataSources.all.find { it.name == name }
                ?: return@post call.respond(
                        HttpStatusCode.NotFound,
                    "Restaurant: $name is not defined as a source."
                    )

            // Haetaan data
            val result = service.syncRestaurantMenu(source)

            // 201 kun uusi viikko luotiin, 200 kun se oli jo synkattu
            val status = if (result.weekCreated) HttpStatusCode.Created else HttpStatusCode.OK
            call.respond(status, result.restaurant)
        }
    }
}