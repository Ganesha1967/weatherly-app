package com.weatherly.weather.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM WeatherCache WHERE city_id = :cityId")
    suspend fun getWeatherCache(cityId: String): WeatherCacheEntity?

    @Query("SELECT * FROM DailyForecast WHERE city_id = :cityId ORDER BY date ASC")
    suspend fun getDailyForecast(cityId: String): List<DailyForecastEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherCache(cache: WeatherCacheEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyForecasts(forecasts: List<DailyForecastEntity>)

    @Query("DELETE FROM DailyForecast WHERE city_id = :cityId")
    suspend fun deleteDailyForecastByCity(cityId: String)

    @Transaction
    suspend fun updateWeatherCacheTransaction(
        cache: WeatherCacheEntity,
        forecasts: List<DailyForecastEntity>,
    ) {
        insertWeatherCache(cache)
        deleteDailyForecastByCity(cache.city_id)
        insertDailyForecasts(forecasts)
    }

    @Query("SELECT * FROM cities WHERE id = :cityId LIMIT 1")
    suspend fun getCityById(cityId: String): CityEntity?

    @Query("SELECT * FROM cities WHERE isFavorite = 1 ORDER BY addedAtMillis DESC LIMIT 5")
    fun getFavorites(): Flow<List<CityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: CityEntity)

    @Query("DELETE FROM cities WHERE id = :cityId")
    suspend fun removeCity(cityId: String)

    @Query("SELECT COUNT(*) FROM cities")
    suspend fun countCities(): Int

    @Query("DELETE FROM cities WHERE id = (SELECT id FROM cities ORDER BY addedAtMillis ASC LIMIT 1)")
    suspend fun removeOldestCity()
}
