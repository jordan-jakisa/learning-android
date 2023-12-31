package com.empire.weatherapp.data.repository

import android.util.Log
import com.empire.weatherapp.data.remote.WeatherAPI
import com.empire.weatherapp.data.remote.mappers.toWeatherInfo
import com.empire.weatherapp.domain.repository.WeatherRepository
import com.empire.weatherapp.domain.util.Resource
import com.empire.weatherapp.domain.util.toNetworkWeather
import com.empire.weatherapp.domain.weather.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherAPI
) : WeatherRepository {
    override suspend fun getWeatherData(lat: Double, lon: Double): Resource<WeatherInfo> {
        return try {
            val data = api.getWeather(
                lat = lat.toString(), lon = lon.toString()
            ).toNetworkWeather()?.toWeatherInfo()
            Resource.Success(
                data = data
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(message = e.message ?: "An unknown error occured!")
        }
    }
}