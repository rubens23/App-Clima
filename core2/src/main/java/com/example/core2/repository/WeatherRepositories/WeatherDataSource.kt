package com.example.core2.repository.WeatherRepositories

import com.example.core2.data.remote.apiclima.OpenWeatherApi
import com.example.core2.data.remote.apiclima.models.OpenWeatherMainDataClass

interface WeatherDataSource {
    fun getWeather(cidade: String)
    fun getLocationFromViewModel(nomeCidade: String?)
    fun getApiInstance(): OpenWeatherApi
    fun getWeatherResponse()
}