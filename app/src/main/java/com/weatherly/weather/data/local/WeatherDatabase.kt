package com.weatherly.weather.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CityEntity::class, WeatherCacheEntity::class, DailyForecastEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}
