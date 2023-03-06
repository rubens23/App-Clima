package com.example.core2.repository.WeatherRepositories


class WeatherRepository(private val dataSource: WeatherDataSource) {

    fun getWeather(cidade: String) = dataSource.getWeather(cidade)
    fun getLocationFromViewModel(nomeCidade: String?) = dataSource.getLocationFromViewModel(nomeCidade)
    fun getApiInstance() = dataSource.getApiInstance()
    fun getWeatherResponse() = dataSource.getWeatherResponse()

}