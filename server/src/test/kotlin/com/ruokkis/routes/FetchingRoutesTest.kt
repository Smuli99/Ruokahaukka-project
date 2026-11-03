package com.ruokkis.routes

import com.ruokkis.config.RestaurantDataSources
import com.ruokkis.models.Restaurant
import com.ruokkis.models.SyncResult
import com.ruokkis.service.RestaurantSyncService
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.http.HttpStatusCode
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation as ClientContentNegotiation
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.application.install
import io.ktor.server.routing.routing
import io.ktor.server.testing.testApplication
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID
import kotlin.test.assertEquals

class FetchingRoutesTest {
    private lateinit var service: RestaurantSyncService

    private val restaurant = Restaurant(
        id = UUID.randomUUID().toString(),
        name = "Piato",
        url = "www.example.com",
        campus = "Mattilanniemi"
    )

    @BeforeEach
    fun setUp() {
        service = mockk()
    }

    private fun ApplicationTestBuilder.setupClient() = run {
        application {
            install(ContentNegotiation) { json() }
            routing { configureFetchingRoutes(service) }
        }
        createClient { install(ClientContentNegotiation) { json() } }
    }

    @Test
    fun `returns 201 created when new restaurant or new week is created`() = testApplication {
        coEvery { service.syncRestaurantMenu(RestaurantDataSources.piato) } returns
                SyncResult(restaurant, weekCreated = true)

        val client = setupClient()
        val response = client.post("/fetch-data/piato")

        assertEquals(HttpStatusCode.Created, response.status)
        assertEquals(restaurant, response.body<Restaurant>())
    }

    @Test
    fun `returns 200 if week was already synced`() = testApplication {
        coEvery { service.syncRestaurantMenu(RestaurantDataSources.piato) } returns
                SyncResult(restaurant, weekCreated = false)

        val client = setupClient()
        val response = client.post("/fetch-data/piato")

        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun `returns 404 for unknown restaurant` () = testApplication {
        val client = setupClient()
        val response = client.post("/fetch-data/unknown")

        assertEquals(HttpStatusCode.NotFound, response.status)
    }
}