package com.example.climaapp.framework.viewModels

import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import com.example.climaapp.framework.UseCases
import com.example.climaapp.framework.data.RetrofitDataSource
import com.example.climaapp.framework.data.remote.apiclima.repositories.WeatherRepository
import com.example.core2.data.remote.apiclima.models.Weather
import com.example.core2.usecase.WeatherUseCases.GetWeather
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ClimaLocalViewModel @Inject constructor(
    private val weatherRepository: com.example.core2.repository.WeatherRepositories.WeatherRepository
) : ViewModel() {

    private var state: MutableLiveData<WeatherRepository.State> = MutableLiveData<WeatherRepository.State>()



    val useCases = UseCases(
        GetWeather(weatherRepository)
    )


    fun getLocationFromActivity(nomeCidade: String?) {
        if (nomeCidade != null){
            useCases.getWeather(nomeCidade)
        }
    }

    fun getWeatherFromViewModel(): MutableLiveData<com.example.core2.data.remote.apiclima.models.OpenWeatherMainDataClass>? = RetrofitDataSource.weatherResponseLiveData

    fun getStateFromViewModel(): LiveData<WeatherRepository.State> = state


    fun initLocationClient(ctx: FragmentActivity): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(ctx)

    fun getCityNameByLocation(location: Location, ctx: FragmentActivity): String?{
        val geoCoder = Geocoder(ctx, Locale.getDefault())
        val adresses: MutableList<Address> =
            geoCoder.getFromLocation(location.latitude, location.longitude, 1)!!
        return adresses[0].subAdminArea


    }


}

