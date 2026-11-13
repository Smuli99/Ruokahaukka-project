package com.ruokkis.routes

import com.ruokkis.database.tables.AllergyTable.id
import com.ruokkis.models.NewRestaurantMenu
import com.ruokkis.models.Restaurant
import com.ruokkis.service.RestaurantService
import io.ktor.http.HttpStatusCode
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

                val restaurantInfo = service.findById(id)

                if (restaurantInfo == null) {
                    call.respond(HttpStatusCode.BadRequest, "Restaurant wasnt found")
                    return@get
                }

                call.respond(restaurantInfo)

            }

            put() {

                //TODO
                // update voisi olla hyvä, jos haluaa muuttaa jotakin, kenttää esim campus, jos tyhjä,
                // voisi ottaa parametrina kentän nimen jota muutetaan, jos ei löydy badRequest
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