package com.weatherly.weather.data.local

import com.weatherly.weather.domain.model.City
import com.weatherly.weather.domain.model.CitySearchResult
import com.weatherly.weather.domain.model.CurrentWeather
import com.weatherly.weather.domain.model.DailyForecast
import com.weatherly.weather.domain.model.WeatherCondition
import com.weatherly.weather.domain.model.WeatherData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomWeatherRepository
    @Inject
    constructor(
        private val db: WeatherDatabase,
    ) {
        private val dao = db.weatherDao()

        val favoritesFlow: StateFlow<List<CitySearchResult>> =
            dao
                .getFavorites()
                .map { cities ->
                    cities.map(CityEntity::toSearchResult)
                }.stateIn(
                    scope = CoroutineScope(Dispatchers.IO),
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = emptyList(),
                )

        suspend fun addFavorite(
            city: CitySearchResult,
            lat: Double,
            lon: Double,
            timezone: String,
        ) {
            if (dao.countCities() >= 5) {
                dao.removeOldestCity()
            }

            dao.insertCity(
                CityEntity(
                    id = city.id,
                    name = city.name,
                    country = city.country,
                    lat = lat,
                    lon = lon,
                    timezone = timezone,
                    isFavorite = true,
                ),
            )
        }

        suspend fun removeFavorite(cityId: String) = dao.removeCity(cityId)

        suspend fun cacheWeather(
            cityId: String,
            data: WeatherData,
        ) {
            val existingCity = dao.getCityById(cityId)

            dao.insertCity(
                data.city.toEntity(
                    cityId = cityId,
                    isFavorite = existingCity?.isFavorite ?: false,
                    addedAtMillis = existingCity?.addedAtMillis ?: System.currentTimeMillis(),
                ),
            )

            dao.updateWeatherCacheTransaction(
                cache = data.toCacheEntity(cityId),
                forecasts = data.dailyForecast.map { it.toEntity(cityId) },
            )
        }

        suspend fun getWeatherCacheData(cityId: String): WeatherData? {
            val cache = dao.getWeatherCache(cityId) ?: return null
            val city = dao.getCityById(cityId)

            return WeatherData(
                city = city.toDomain(cityId),
                current = cache.toDomain(),
                dailyForecast =
                    dao
                        .getDailyForecast(cityId)
                        .map(DailyForecastEntity::toDomain),
                hourlyForecast = emptyList(),
            )
        }

        suspend fun getCityById(cityId: String): CityEntity? = dao.getCityById(cityId)
    }

private fun CityEntity.toSearchResult() =
    CitySearchResult(
        id = id,
        name = name,
        country = country,
        lat = lat,
        lon = lon,
    )

private fun City.toEntity(
    cityId: String,
    isFavorite: Boolean,
    addedAtMillis: Long,
) = CityEntity(
    id = cityId,
    name = name,
    country = country,
    lat = lat,
    lon = lon,
    timezone = timezone,
    isFavorite = isFavorite,
    addedAtMillis = addedAtMillis,
)

private fun WeatherData.toCacheEntity(cityId: String) =
    WeatherCacheEntity(
        city_id = cityId,
        current_temp = current.temp,
        temp_min = current.tempMin,
        temp_max = current.tempMax,
        feels_like = current.feelsLike,
        weather_code = current.condition.wmoCode,
        humidity = current.humidity,
        pressure = current.pressure,
        wind_speed = current.windSpeed,
        precipitation = current.precipitation,
        uv_index = current.uvIndex.toDouble(),
        cloud_cover = current.cloudCover,
        sunrise = current.sunrise,
        sunset = current.sunset,
        fetched_at_millis = System.currentTimeMillis(),
    )

private fun DailyForecast.toEntity(cityId: String) =
    DailyForecastEntity(
        city_id = cityId,
        date = date,
        day_name = dayName,
        min_temp = tempMin,
        max_temp = tempMax,
        weather_code = condition.wmoCode,
        precipitation_chance = precipitationChance,
    )

private fun DailyForecastEntity.toDomain() =
    DailyForecast(
        dayName = day_name,
        date = date,
        icon = "",
        tempMin = min_temp,
        tempMax = max_temp,
        condition = WeatherCondition.fromWmoCode(weather_code),
        precipitationChance = precipitation_chance,
    )

private fun WeatherCacheEntity.toDomain() =
    CurrentWeather(
        temp = current_temp,
        tempMin = temp_min,
        tempMax = temp_max,
        feelsLike = feels_like,
        condition = WeatherCondition.fromWmoCode(weather_code),
        description = "",
        humidity = humidity,
        pressure = pressure,
        windSpeed = wind_speed,
        precipitation = precipitation,
        precipitationFloat = precipitation / 100f,
        uvIndex = uv_index.toFloat(),
        cloudCover = cloud_cover,
        sunrise = sunrise,
        sunset = sunset,
    )

private fun CityEntity?.toDomain(cityId: String) =
    City(
        id = cityId,
        name = this?.name.orEmpty(),
        country = this?.country.orEmpty(),
        lat = this?.lat ?: 0.0,
        lon = this?.lon ?: 0.0,
        timezone = this?.timezone ?: "UTC",
    )
