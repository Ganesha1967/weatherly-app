package com.weatherly.weather.di

import android.content.Context
import androidx.room.Room
import com.weatherly.weather.data.local.WeatherDao
import com.weatherly.weather.data.local.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): WeatherDatabase =
        Room
            .databaseBuilder(context, WeatherDatabase::class.java, "weatherly.db")
            .fallbackToDestructiveMigration(false)
            .build()

    @Provides
    fun provideWeatherDao(database: WeatherDatabase): WeatherDao = database.weatherDao()
}
