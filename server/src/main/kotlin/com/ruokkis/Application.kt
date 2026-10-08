package com.ruokkis

import com.ruokkis.service.PiatoService
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.runBlocking

fun main() {
//    Väliaikainen ratkaisu poistetaan, kun datan haku todettu toimivaksi
    runBlocking {
        val menu = PiatoService().fetchMenu()
        println("Menu: $menu")
        println(menu.restaurantName)
        menu.days.forEach { day ->
            println("${day.date.take(10)} (${day.lunchTime})")
            day.menu.filter { it.dishes.isNotEmpty() }.forEach { meal ->
                println(" ${meal.name}: ${meal.dishes}")
            }
        }
    }
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val piatoService = PiatoService()


    install(ContentNegotiation) {
        json()
    }

    routing {
        get("/") {
            call.respondText(sayHello("Ktor"))
        }
        get("/piato") {
            try {
                call.respond(piatoService.fetchMenu())
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}