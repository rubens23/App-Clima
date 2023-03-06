package com.example.core2.repository.CountriesRepositories

class CountryRepository(private val dataSource: CountryDataSource) {
    fun getCountry() = dataSource.getCountry()
    fun getCountryResponse() = dataSource.getCountryResponse()
    fun getCapitalFromGlobalFragment(capital: String) = dataSource.getCapitalFromGlobalFragment(capital)

}