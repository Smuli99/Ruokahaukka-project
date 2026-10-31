package com.ruokkis

import com.ruokkis.service.HttpRestaurantService
import com.ruokkis.service.RestaurantNotFoundException
import com.ruokkis.service.UpstreamException
import com.ruokkis.service.database.AppDatabaseImpl
import com.ruokkis.service.restaurantRoutes
import com.ruokkis.service.routes.testRoutes
import io.ktor.client.*
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation as ClientContentNegotiation
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respondText
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

/**
 * Käynnistetään sovellus
 *
 * Nettypalvelin käynnistetään portissa 8080, jonka jälkeen ajetaan module funktio, mikä määritelty alla.
 * host = "0.0.0.0" -> palvelimeen pääsee muilla koneilla esim. puhelimella samassa verkossa
 */
fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

/**
 * Lisätty testejä varten moduulin parametriksi HttpClientEngine.
 * Tuotannossa luo CIO enginen oletuksena ja testeissä kutsutaan halutulla enginellä.
 *
 * 1. Luodaan HttpClient, joilla ravintoloiden dataa haetaan
 * 2. Listataan ravintolat
 * 3. Asennetaan JSON-muunnos ja virheiden käsittely
 * 4. Lisätään reitit
 */
fun Application.module(engine: HttpClientEngine = CIO.create()) {
    val database = AppDatabaseImpl()

    val client = HttpClient(engine) {
        install(ClientContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    // Kun palvelin sammuu suljetaan HttpClient, jotta avoimet yhteydet ja säikeet vapautuu
    monitor.subscribe(ApplicationStopped) { client.close() }

    // Ravintolat -> tähän voi lisätä uusia
    val services = listOf(
        HttpRestaurantService(
            id = "piato",
            url = "https://www.semma.fi/menuapi/feed/json?costNumber=1408&language=fi",
            client = client,
        ),
    ).associateBy { it.id } // mutetaan Mapiksi ("piato" to HttpRestaurantService(...))


    install(ContentNegotiation) { json() }

    // Exceptionit muuttuvat status koodeksi
    install(StatusPages) {
        exception<RestaurantNotFoundException> { call, e ->
            call.respondText("Tuntematon ravintola: ${e.id}", status = HttpStatusCode.NotFound)
        }
        exception<UpstreamException> { call, _ ->
            call.respondText("Ravintolan palvelu ei vastaa", status = HttpStatusCode.BadGateway)
        }
    }

    // Reitit
    routing {
        restaurantRoutes(services)
        testRoutes(database)
    }
}