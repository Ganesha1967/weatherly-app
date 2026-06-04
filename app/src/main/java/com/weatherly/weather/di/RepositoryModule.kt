package com.weatherly.weather.di

import com.weatherly.weather.data.repositories.ApiWeatherRepository
import com.weatherly.weather.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideWeatherRepository(apiWeatherRepository: ApiWeatherRepository): WeatherRepository = apiWeatherRepository
}
