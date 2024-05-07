package com.den3000.kmpwaystoaurora.shared.spacexlaunches

import com.den3000.kmpwaystoaurora.shared.getHttpRequestClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
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
        return httpClient?.get(URL)?.body() ?: emptyList()
    }

    suspend fun getAllLaunchesJsonText(): String {
        return httpClient?.get(URL)?.bodyAsText() ?: ""
    }

    fun close() {
        httpClient?.close()
    }

    companion object {
        private val URL = "https://api.spacexdata.com/v5/launches"
    }
}