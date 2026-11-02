package com.ruokkis.routes

import com.ruokkis.models.NewRestaurant
import com.ruokkis.models.Restaurant
import com.ruokkis.service.RestaurantService2
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation as ClientContentNegotiation
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import io.ktor.server.application.install
import io.ktor.server.routing.routing
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.UUID

class RestaurantRoutesTest {
    private lateinit var service: RestaurantService2

    @BeforeEach
    fun setUp() {
        service = mockk()
    }

    // Käynnistetään palvelin mockatulla servicellä
    private fun ApplicationTestBuilder.setupClient() = run {
        application {
            install(ContentNegotiation) { json() }
            routing { restaurantRoutes2(service) }
        }
        createClient { install(ClientContentNegotiation) { json() } }
    }

    @Test
    fun `GET restaurants returns restaurants`() = testApplication {
        val restaurants = listOf(
            Restaurant(
                id = UUID.randomUUID().toString(),
                name = "Piato",
                url = "https://www.piato.com/",
                campus = "Agora"
            )
        )

        coEvery { service.getRestaurants() } returns restaurants
        val client = setupClient()

        val response = client.get("/restaurants")

        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(restaurants, response.body<List<Restaurant>>())
    }

    @Nested
    inner class PostRestaurant {
        @Test
        fun `POST with valid data returns 201 and created restaurant`() = testApplication {
            val newRestaurant = NewRestaurant(
                name = "Piato",
                url = "https://www.piato.com/",
                campus = "Agora"
            )

            val expectedResult = Restaurant(
                id = UUID.randomUUID().toString(),
                name = "Piato",
                url = "https://www.piato.com/",
                campus = "Agora"
            )

            coEvery { service.createRestaurant(newRestaurant) } returns expectedResult
            val client = setupClient()

            val response = client.post("/restaurants") {
                contentType(ContentType.Application.Json)
                setBody(newRestaurant)
            }

            assertEquals(HttpStatusCode.Created, response.status)
            assertEquals(expectedResult, response.body<Restaurant>())
            coVerify(exactly = 1) { service.createRestaurant(newRestaurant) }
        }

        @Test
        fun `POST fails with statuscode 400 if name is missing`() = testApplication {
            val newRestaurant = NewRestaurant(
                name = "",
                url = "https://www.piato.com/",
                campus = "Agora"
            )

            val client = setupClient()

            val response = client.post("/restaurants") {
                contentType(ContentType.Application.Json)
                setBody(newRestaurant)
            }

            assertEquals(HttpStatusCode.BadRequest, response.status)
            assertEquals("Name cannot be empty", response.bodyAsText())
            coVerify(exactly = 0) { service.createRestaurant(newRestaurant) }
        }

        @Test
        fun `Post fails with statuscode 400 if url is missing`() = testApplication {
            val newRestaurant = NewRestaurant(
                name = "Piato",
                url = "",
                campus = "Agora"
            )

            val client = setupClient()

            val response = client.post("/restaurants") {
                contentType(ContentType.Application.Json)
                setBody(newRestaurant)
            }

            assertEquals(HttpStatusCode.BadRequest, response.status)
            assertEquals("Url cannot be empty", response.bodyAsText())
            coVerify(exactly = 0) { service.createRestaurant(newRestaurant) }
        }

        @Test
        fun `Post fails with statuscode 400 if url is invalid`() = testApplication {
            val newRestaurant = NewRestaurant(
                name = "Piato",
                url = "piato.com",
                campus = "Agora"
            )

            val client = setupClient()

            val response = client.post("/restaurants") {
                contentType(ContentType.Application.Json)
                setBody(newRestaurant)
            }

            assertEquals(HttpStatusCode.BadRequest, response.status)
            assertEquals("Not a valid url", response.bodyAsText())
            coVerify(exactly = 0) { service.createRestaurant(newRestaurant) }
        }

        @Test
        fun `Post fails with statuscode 409 if restaurant already exists`() = testApplication {
            val newRestaurant = NewRestaurant(
                name = "Piato",
                url = "https://www.piato.com/",
                campus = "Agora"
            )

            coEvery { service.createRestaurant(newRestaurant) } returns null
            val client = setupClient()

            val response = client.post("/restaurants") {
                contentType(ContentType.Application.Json)
                setBody(newRestaurant)
            }

            assertEquals(HttpStatusCode.Conflict, response.status)
            assertEquals("Restaurant with name '${newRestaurant.name}' already exists", response.bodyAsText())
            coVerify(exactly = 1) { service.createRestaurant(newRestaurant) }
        }
    }
}