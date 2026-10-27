package com.ruokkis

import com.ruokkis.service.HttpRestaurantService
import com.ruokkis.service.RestaurantNotFoundException
import com.ruokkis.service.UpstreamException
import com.ruokkis.service.restaurantRoutes
import io.ktor.client.*
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
 * 1. Luodaan HttpClient, joilla ravintoloiden dataa haetaan
 * 2. Listataan ravintolat
 * 3. Asennetaan JSON-muunnos ja virheiden käsittely
 * 4. Lisätään reitit
 */
fun Application.module() {
    val client = HttpClient(CIO) {
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
        HttpRestaurantService(
            id = "lozzi",
            url = "https://www.semma.fi/menuapi/feed/json?costNumber=1401&language=fi",
            client = client,
        ),
        HttpRestaurantService(
            id = "ilokivi",
            url = "https://www.semma.fi/menuapi/feed/json?costNumber=1419&language=fi",
            client = client,
        ),
        HttpRestaurantService(
            id = "uno",
            url = "https://www.semma.fi/menuapi/feed/json?costNumber=1414&language=fi",
            client = client,
        ),
        HttpRestaurantService(
            id = "ylisto", // kirjoitettu o:lla ö:n sijasta
            url = "https://www.semma.fi/menuapi/feed/json?costNumber=1403&language=fi",
            client = client,
        ),
        HttpRestaurantService(
            id = "rentukka", // kahvila
            url = "https://www.semma.fi/menuapi/feed/json?costNumber=1416&language=fi",
            client = client,
        ),
        HttpRestaurantService(
            id = "belvedere", // kahvila
            url = "https://www.semma.fi/menuapi/feed/json?costNumber=1404&language=fi",
            client = client,
        ),
        HttpRestaurantService(
            id = "syke", // kahvila
            url = "https://www.semma.fi/menuapi/feed/json?costNumber=1405&language=fi",
            client = client,
        ),
        HttpRestaurantService(
            id = "taide", // Sisältää myös kahvila Tieteen (Kirjasto Lähde)
            url = "https://www.compass-group.fi/menuapi/feed/json?costNumber=0301&language=fi",
            client = client,
        ),
        HttpRestaurantService(
            id = "bistro",
            url = "https://www.compass-group.fi/menuapi/feed/json?costNumber=3081&language=fi",
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
    }
}