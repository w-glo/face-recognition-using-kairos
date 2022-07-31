package com.wajahat.facerecognition

/**
 * Created by Wajahat Jawaid(wajahatjawaid@gmail.com)
 */
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.http.*
import timber.log.Timber

private const val TIME_OUT = 60_000

val ktorHttpClient = HttpClient(Android) {

    install(JsonFeature) {
        Timber.d("Installing JsonFeature")
        serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })

        engine {
            connectTimeout = TIME_OUT
            socketTimeout = TIME_OUT
        }
    }

    install(Logging) {
        Timber.d("Installing Logging")
        logger = object : Logger {
            override fun log(message: String) {
                Timber.d("Logger Ktor =>: %s", message)
            }

        }
        level = LogLevel.INFO
    }

    install(ResponseObserver) {
        Timber.d("Installing ResponseObserver")
        onResponse { response ->
            Timber.d("HTTP status: %d", response.status.value)
        }
    }

    install(DefaultRequest) {
        Timber.d("Installing DefaultRequest")
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        header("Content-Type", "application/json")
        header("app_id", "bb3c0e3d")
        header("app_key", "74aa45ae066df94e34d04eb4d536dcdb")
    }
}