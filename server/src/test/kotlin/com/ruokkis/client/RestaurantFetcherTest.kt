package com.ruokkis.client

import com.ruokkis.config.RestaurantDataSources
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class RestaurantFetcherTest {

    // Rakennetaan fetcher mockEnginellä
    private fun buildFetcher(json: String, status: HttpStatusCode): RestaurantFetcher {
        val mockEngine = MockEngine {
            respond(
                content = json,
                status = status,
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }
        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json( Json { ignoreUnknownKeys = true })
            }
        }
        return RestaurantFetcher(client)
    }

    @Test
    fun `fetching restaurant data converts json correctly`() = runTest {
        val fetcher = buildFetcher(jsonResponse, HttpStatusCode.OK)
        val result = fetcher.fetch(RestaurantDataSources.ilokivi)

        assertEquals(expectedResult, result)
    }
}