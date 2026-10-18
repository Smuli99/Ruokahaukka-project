package com.ruokkis.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun createHttpClient() = HttpClient {
    install(ContentNegotiation) {
        json(Json {
            encodeDefaults = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
    install(HttpTimeout) {
        connectTimeoutMillis = 30000
        requestTimeoutMillis = 15000
        socketTimeoutMillis = 15000
    }

    val serverHost = getServerHost()

    defaultRequest {
        host = serverHost.host
        port = serverHost.port
    }
}
