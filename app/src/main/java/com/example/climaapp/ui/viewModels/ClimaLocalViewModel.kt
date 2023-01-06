package com.example.climaapp.ui.viewModels

import android.app.Application
import android.location.Location
import androidx.lifecycle.*
import com.example.climaapp.data.remote.apiclima.models.OpenWeatherMainDataClass
import com.example.climaapp.data.remote.apiclima.repositories.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ClimaLocalViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private lateinit var location: LiveData<Location>
    private var state: MutableLiveData<WeatherRepository.State> = MutableLiveData<WeatherRepository.State>()

    private var weatherResponseLiveData: MutableLiveData<OpenWeatherMainDataClass>? = weatherRepository.getWeatherResponse()

    init {
        state = weatherRepository.getState()

    }

    fun getLocationFromViewModel(): LiveData<Location> = location
    fun getLocationFromActivity(nomeCidade: String?) {
        weatherRepository.getLocationFromViewModel(nomeCidade)
    }

    fun getWeatherFromViewModel(): MutableLiveData<OpenWeatherMainDataClass>? = weatherResponseLiveData

    fun getStateFromViewModel(): LiveData<WeatherRepository.State> = state

}

/*
class MainActivityViewModelFactory constructor(
    private val application: Application
): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainActivityViewModel::class.java)){
            return MainActivityViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

 */