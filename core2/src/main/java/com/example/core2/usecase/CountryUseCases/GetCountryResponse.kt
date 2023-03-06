package com.example.core2.usecase.CountryUseCases

import com.example.core2.repository.CountriesRepositories.CountryRepository

class GetCountryResponse(private val countryRepository: CountryRepository) {
    fun invoke() = countryRepository.getCountryResponse()
}