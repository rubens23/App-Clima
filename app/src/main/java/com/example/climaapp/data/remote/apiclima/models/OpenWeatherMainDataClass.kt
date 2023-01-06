package com.example.climaapp.data.remote.apiclima.models

import com.google.gson.annotations.SerializedName

data class OpenWeatherMainDataClass(
    @SerializedName("base")
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind,
    @SerializedName("dt_txt")
    val dtTxt: String
)
