package spacexlaunches

import getHttpRequestClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class Api {
    private val httpClient = getHttpRequestClient()?.config {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }

    suspend fun getAllLaunches(): List<RocketLaunch> {
        return httpClient?.get("https://api.spacexdata.com/v5/launches")?.body() ?: emptyList()
    }

    fun close() {
        httpClient?.close()
    }
}