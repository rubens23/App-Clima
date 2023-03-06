package com.example.core2.usecase.CountryUseCases

import com.example.core2.repository.CountriesRepositories.CountryRepository

class GetCapitalFromGlobalFragment(private val countryRepository: CountryRepository) {
    fun invoke(capital: String) = countryRepository.getCapitalFromGlobalFragment(capital)

}