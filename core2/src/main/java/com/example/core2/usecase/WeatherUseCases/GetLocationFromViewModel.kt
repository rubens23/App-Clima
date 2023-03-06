package com.example.core2.usecase.WeatherUseCases

import com.example.core2.repository.WeatherRepositories.WeatherRepository

class GetLocationFromViewModel(private val weatherRepository: WeatherRepository) {
    fun invoke(cidade: String?) = weatherRepository.getLocationFromViewModel(cidade)
}