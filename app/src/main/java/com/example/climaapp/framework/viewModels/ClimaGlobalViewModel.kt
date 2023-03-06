package com.example.climaapp.framework.viewModels

import android.app.Application
import androidx.lifecycle.*
import com.example.climaapp.framework.data.remote.apicountries.models.CountryMainModel
import com.example.climaapp.framework.data.remote.apicountries.repositories.CountryRepository

class ClimaGlobalViewModel(application: Application): AndroidViewModel(application) {
    private var countryRepository: CountryRepository = CountryRepository()
    private var state: MutableLiveData<CountryRepository.State> = MutableLiveData<CountryRepository.State>()

    private var countryResponseLiveData: MutableLiveData<List<CountryMainModel>>? = countryRepository.getCountryResponse()

    init{
        countryRepository.getCountry()
        state = countryRepository.getState()
    }

    fun getCountryFromViewModel(): MutableLiveData<List<CountryMainModel>>? = countryResponseLiveData

    fun getStateFromViewModel(): LiveData<CountryRepository.State> = state
}

class ClimaGlobalViewModelFactory constructor(
    private val application: Application
): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ClimaGlobalViewModel::class.java)){
            return ClimaGlobalViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}