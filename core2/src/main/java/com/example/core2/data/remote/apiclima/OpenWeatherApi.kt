package com.example.core2.data.remote.apiclima

import com.example.core2.data.remote.apiclima.models.OpenWeatherMainDataClass
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

    @GET("data/2.5/weather?")
    fun getCurrentWeatherByCity(
        @Query("q") city: String,
        @Query("appid") app_id: String,
        @Query("units") units: String = "metric"
    ): Call<OpenWeatherMainDataClass>
}