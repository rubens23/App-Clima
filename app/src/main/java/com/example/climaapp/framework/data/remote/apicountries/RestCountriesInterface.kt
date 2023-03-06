package com.example.climaapp.framework.data.remote.apicountries

import com.example.climaapp.framework.data.remote.apicountries.models.CountryMainModel
import retrofit2.Call
import retrofit2.http.GET

interface RestCountriesInterface {
    @GET("all")
    fun getAllCountries(): Call<List<CountryMainModel>>
}