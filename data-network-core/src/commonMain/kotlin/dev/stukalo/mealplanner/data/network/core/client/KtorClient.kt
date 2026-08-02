package dev.stukalo.mealplanner.data.network.core.client

import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val TIME_OUT = 15_000L

internal fun client() = HttpClient {
    expectSuccess = true

    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            }
        )
    }

    install(HttpTimeout) {
        connectTimeoutMillis = TIME_OUT
        requestTimeoutMillis = TIME_OUT
        socketTimeoutMillis = TIME_OUT
    }

    install(Logging) {
        logger = Logger.SIMPLE
        level = LogLevel.ALL
    }

    install(DefaultRequest) {
        headers.apply {
            append("Content-Type", "application/json")
            append("platform", "android")
        }
        url {
            protocol = URLProtocol.HTTPS
        }
    }
}
