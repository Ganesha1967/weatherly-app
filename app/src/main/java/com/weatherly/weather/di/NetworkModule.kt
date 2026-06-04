package com.weatherly.weather.di

import com.weatherly.weather.data.remote.WeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideJson(): Json =
        Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
            prettyPrint = false
        }

    @Provides
    @Singleton
    fun provideHttpClient(json: Json): HttpClient =
        HttpClient {
            install(ContentNegotiation) { json(json) }
            install(Logging) {
                level = LogLevel.INFO
            }
        }

    @Provides
    @Singleton
    fun provideWeatherApiService(httpClient: HttpClient): WeatherApiService = WeatherApiService(httpClient)
}
