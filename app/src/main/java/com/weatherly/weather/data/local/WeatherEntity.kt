package com.weatherly.weather.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
data class CityEntity(
    @PrimaryKey val id: String,
    val name: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val isFavorite: Boolean = false,
    val addedAtMillis: Long = System.currentTimeMillis(),
)

@Entity(
    tableName = "DailyForecast",
    foreignKeys = [
        ForeignKey(
            entity = CityEntity::class,
            parentColumns = ["id"],
            childColumns = ["city_id"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index(value = ["city_id"])],
)
data class DailyForecastEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val city_id: String,
    val date: String,
    val day_name: String,
    val min_temp: Int,
    val max_temp: Int,
    val weather_code: Int,
    val precipitation_chance: Int,
)

@Entity(
    tableName = "WeatherCache",
    foreignKeys = [
        ForeignKey(
            entity = CityEntity::class,
            parentColumns = ["id"],
            childColumns = ["city_id"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class WeatherCacheEntity(
    @PrimaryKey val city_id: String,
    val current_temp: Int,
    val temp_min: Int,
    val temp_max: Int,
    val feels_like: Int,
    val weather_code: Int,
    val humidity: Int,
    val pressure: Int,
    val wind_speed: Double,
    val precipitation: Int,
    val uv_index: Double,
    val cloud_cover: Int,
    val sunrise: String,
    val sunset: String,
    val fetched_at_millis: Long,
)
