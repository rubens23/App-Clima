package com.example.climaapp.framework

import com.example.core2.usecase.WeatherUseCases.GetWeather


data class UseCases(
    val getWeather: GetWeather
)