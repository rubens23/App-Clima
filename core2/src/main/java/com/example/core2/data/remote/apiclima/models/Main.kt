package com.example.core2.data.remote.apiclima.models

import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("feels_like")
    val feelsLike: Double,
    val humidity: Int,
    val pressure: Int,
    @SerializedName("temp")
    val temperature: Double,
    @SerializedName("temp_max")
    val maxTemperature: Double,
    @SerializedName("temp_min")
    val minTemperature: Double
)
