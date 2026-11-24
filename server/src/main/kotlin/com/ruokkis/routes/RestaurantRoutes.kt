package com.ruokkis.routes

import com.ruokkis.model.NewRestaurantMenu
import com.ruokkis.model.Restaurant
import com.ruokkis.service.RestaurantService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import java.util.Objects.toString
import java.util.UUID
import kotlin.time.Clock


fun Route.configureRestaurantRoutes(
    service: RestaurantService
) {

    route("/restaurants") {
        get {
            val restaurants = service.getRestaurants()
            call.respond(restaurants)
        }

        get("/details") {
            try {
                val restaurants = service.getRestaurantsDetails()
                call.respond(HttpStatusCode.OK, restaurants)
            } catch (e: Exception) {
                println(e.toString())
            }
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

            val newRestaurant: Restaurant? = service.createRestaurant(restaurantData)

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


            //Hakee tietyn ravintolan tiedot tietokannasta id:n perusteella
            get() {
                val id = call.uuidParam() ?: return@get

                val restaurantInfo = service.findById(id)
                if (restaurantInfo == null) {
                    call.respond(HttpStatusCode.BadRequest, "Restaurant wasnt found")
                    return@get
                }

                call.respond(restaurantInfo)
            }

            // Ravintolan suosikki kentän muokkaus
            put() {
                val id = call.uuidParam() ?: return@put

                val updatedRestaurant = service.updateRestaurant(id)
                call.respond(HttpStatusCode.OK, updatedRestaurant)
            }


            //Poistaa ravintolan tiedot id:n perusteella
            delete() {

                val idString = call.parameters["id"]

                if (idString == null) {
                    call.respond(HttpStatusCode.BadRequest, "Id is empty")
                    return@delete
                }

                val id = try { //Yritetään muuttaa String id UUID muotoon
                    UUID.fromString(idString) // Muuttaa saadun idn stringistä UUID -> toimii lopuissa
                }catch (e: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid UUID") //Heittää error coce 400 + ton msg käyttäjälle, jso ei toimi
                    return@delete // jotta lähetään pois
                }

                val restaurantDeleted = service.deleteById(id) //palauttaa true, jos poistaminen onnistui, false jos ei

                if (restaurantDeleted) call.respond(HttpStatusCode.NoContent, "Deleted succesfully")
                else call.respond(HttpStatusCode.InternalServerError, "Failed to delete")

            }

            // /restaurants/{id}/menu
            route("/menu") {

                get() {

                    val idString = call.parameters["id"]

                    if (idString == null) {
                        call.respond(HttpStatusCode.BadRequest, "Id is empty")
                        return@get
                    }

                    val id = try { //Yritetään muuttaa String id UUID muotoon
                        UUID.fromString(idString) // Muuttaa saadun idn stringistä UUID -> toimii lopuissa
                    }catch (e: IllegalArgumentException) {
                        call.respond(HttpStatusCode.BadRequest, "Invalid UUID") //Heittää error coce 400 + ton msg käyttäjälle, jso ei toimi
                        return@get // jotta lähetään pois
                    }


                    val localDate = Clock.System.todayIn(TimeZone.currentSystemDefault()) //LocalDare on erimuoto? Ei käy, eikä javanomakaa, ent tiiä onks tää järkevin

                   //Muutettu id to String, alkuperänen käytti UUID, mut findWeekMenua käyttää nii moni et tää on helpompi korjata
                    //En tiiä kannattaisko toi olemassaolon chekkaus muuttaa, mut toimii nii antaa olla?
                    val weekMenu = service.findWeekMenu(toString(id), localDate) //return WeekMenu


                    if (weekMenu == null) {
                        call.respond(HttpStatusCode.BadRequest, "Menu wasnt found")
                        return@get
                    }

                    call.respond(weekMenu)
                }

                delete(){
                    //TODO poistaa viikkomenun
                }

                // /restaurants/{id}/menu/today
                get("/today") {


                    //testaa onko restaurant olemassa
                    val idString = call.parameters["id"]

                    if (idString == null) {
                        call.respond(HttpStatusCode.BadRequest, "Id is empty")
                        return@get
                    }

                    val id = try { //Yritetään muuttaa String id UUID muotoon
                        UUID.fromString(idString) // Muuttaa saadun idn stringistä UUID -> toimii lopuissa
                    }catch (e: IllegalArgumentException) {
                        call.respond(HttpStatusCode.BadRequest, "Invalid UUID") //Heittää error coce 400 + ton msg käyttäjälle, jso ei toimi
                        return@get // jotta lähetään pois
                    }


                    val localDate = Clock.System.todayIn(TimeZone.currentSystemDefault()) //LocalDare on erimuoto? Ei käy, eikä javanomakaa, ent tiiä onks tää järkevin

                    val dayMenu = service.findDayMenu(toString(id), localDate)
                    //Pitäskö localDate sijaan käyttää tota parametria?


                    if (dayMenu == null) {
                        call.respond(HttpStatusCode.BadRequest, "Menu wasnt found")
                        return@get
                    }

                    call.respond(dayMenu)


                }


                // /restaurants/{id}/menu/day/{weekday} esim. ke
                get("/day/{weekday}") {

                    //testaa onko restaurant olemassa
                    val idString = call.parameters["id"]

                    val weekday = call.parameters["weekday"]

                    if (idString == null) {
                        call.respond(HttpStatusCode.BadRequest, "Id is empty")
                        return@get
                    }

                    if (weekday == null) {
                        call.respond(HttpStatusCode.BadRequest, "No day found")
                        return@get
                    }

                    val id = try { //Yritetään muuttaa String id UUID muotoon
                        UUID.fromString(idString) // Muuttaa saadun idn stringistä UUID -> toimii lopuissa
                    }catch (e: IllegalArgumentException) {
                        call.respond(HttpStatusCode.BadRequest, "Invalid UUID") //Heittää error coce 400 + ton msg käyttäjälle, jso ei toimi
                        return@get // jotta lähetään pois
                    }

                    val dayMenu = service.findWeekdayMenu(toString(id), weekday)


                    if (dayMenu == null) {
                        call.respond(HttpStatusCode.BadRequest, "Menu wasnt found")
                        return@get
                    }

                    call.respond(dayMenu)


                }

                // /restaurants/{id}/menu/day/{date} esim. 2026-11-05
                get("/day/{date}") {
                    //testaa onko restaurant olemassa
                    val idString = call.parameters["id"]

                    val date = call.parameters["date"]

                    if (idString == null) {
                        call.respond(HttpStatusCode.BadRequest, "Id is empty")
                        return@get
                    }

                    if (date == null) {
                        call.respond(HttpStatusCode.BadRequest, "No day found")
                        return@get
                    }

                    val id = try { //Yritetään muuttaa String id UUID muotoon
                        UUID.fromString(idString) // Muuttaa saadun idn stringistä UUID -> toimii lopuissa
                    }catch (e: IllegalArgumentException) {
                        call.respond(HttpStatusCode.BadRequest, "Invalid UUID") //Heittää error coce 400 + ton msg käyttäjälle, jso ei toimi
                        return@get // jotta lähetään pois
                    }

                    val dayMenu = service.findDateMenu(toString(id), LocalDate.parse(date).toString())


                    if (dayMenu == null) {
                        call.respond(HttpStatusCode.BadRequest, "Menu wasnt found")
                        return@get
                    }

                    call.respond(dayMenu)
                }
            }
        }
    }

    route("/foods") {
        get() {
            val foods = service.getFoods()
            call.respond(HttpStatusCode.OK, foods)
        }

        route("/{id}") {
            get() {
                val id = call.uuidParam() ?: return@get

                val food = service.getFoodById(id)
                if (food == null) {
                    call.respond(HttpStatusCode.BadRequest, "No food found")
                    return@get
                }

                call.respond(HttpStatusCode.OK, food)
            }

            // voidaan muuttaa favorite kenttää
            put() {
                val id = call.uuidParam() ?: return@put

                val updatedFood = service.updateFood(id)
                call.respond(HttpStatusCode.OK, updatedFood)
            }
        }
    }

    route("/allergies") {
        get() {
            val allergies = service.getAllergies()
            call.respond(HttpStatusCode.OK, allergies)
        }
    }
}

// Apu funktio, joka ottaa id:n suoraan uuid muodossa (poistaa toistoa)
private suspend fun ApplicationCall.uuidParam(name: String = "id"): UUID? {
    val raw = parameters[name]
    if (raw == null) {
        respond(HttpStatusCode.BadRequest, "$name is missing")
        return null
    }
    return try {
        UUID.fromString(raw)
    } catch (e: IllegalArgumentException) {
        respond(HttpStatusCode.BadRequest, "Invalid UUID")
        null
    }
}