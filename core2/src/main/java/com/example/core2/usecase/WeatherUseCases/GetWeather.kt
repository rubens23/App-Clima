package com.example.core2.usecase.WeatherUseCases

import com.example.core2.repository.WeatherRepositories.WeatherRepository

class GetWeather(private val weatherRepository: WeatherRepository) {
    operator fun invoke(cidade: String) = weatherRepository.getWeather(cidade)
}