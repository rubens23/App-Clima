package com.example.climaapp.framework.viewModels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.climaapp.framework.UseCases
import com.example.climaapp.framework.data.RetrofitDataSource
import com.example.climaapp.framework.data.remote.apiclima.repositories.WeatherRepository
import com.example.core2.data.remote.apiclima.OpenWeatherApi
import com.example.core2.usecase.WeatherUseCases.GetWeather
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ClimaCapitalViewModel @Inject constructor(
    private val weatherRepository: com.example.core2.repository.WeatherRepositories.WeatherRepository
): ViewModel(){



    //private var weatherResponseLiveData: MutableLiveData<OpenWeatherMainDataClass>? = weatherRepository.getWeatherResponse()
    private var state: MutableLiveData<WeatherRepository.State> = MutableLiveData<WeatherRepository.State>()

    //val repository = com.example.core2.repository.WeatherRepositories.WeatherRepository(RetrofitDataSource(weatherRepository.getApiInstance()))

    val useCases = UseCases(
        GetWeather(weatherRepository)
    )
//
//    fun getWeather(cidade: String){
//        useCases.getWeather(cidade)
//
//    }

    fun getWeatherFromViewModel(): MutableLiveData<com.example.core2.data.remote.apiclima.models.OpenWeatherMainDataClass>? = RetrofitDataSource.weatherResponseLiveData




    //todo talvez eu consiga passar esse metodo para o modulo
    fun getCapitalFromGlobalFragment(capital: String){
        useCases.getWeather(capital)
    }


    fun getStateFromViewModel(): LiveData<WeatherRepository.State> = state

}