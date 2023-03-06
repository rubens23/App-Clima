package com.example.core2.repository.CountriesRepositories

import com.example.core2.data.remote.apiclima.OpenWeatherApi
import com.example.core2.data.remote.apicountries.models.CountryMainModel

interface CountryDataSource{


    fun getCountry()
    fun getCountryResponse(): CountryMainModel
    fun getCapitalFromGlobalFragment(capital: String)
}