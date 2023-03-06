package com.example.climaapp.framework.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.climaapp.framework.data.remote.apiclima.repositories.WeatherRepository
import com.example.core2.data.remote.apiclima.OpenWeatherApi
import com.example.core2.data.remote.apiclima.models.OpenWeatherMainDataClass
import com.example.core2.repository.WeatherRepositories.WeatherDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class RetrofitDataSource @Inject constructor(
    private val openWeatherApi: OpenWeatherApi
): WeatherDataSource {

    companion object{
        const val BASE_URL = "https://api.openweathermap.org/"
        const val API_KEY = "54454b2259a59ae51ffe392ca35d653c"
        var weatherResponseLiveData: MutableLiveData<OpenWeatherMainDataClass>? = MutableLiveData()
    }


    override fun getApiInstance() = openWeatherApi

    override fun getWeatherResponse() {
        TODO("Not yet implemented")
    }


    override fun getWeather(cidade: String) {
        openWeatherApi.getCurrentWeatherByCity(cidade, API_KEY).enqueue(object : Callback<OpenWeatherMainDataClass>{
            override fun onResponse(
                call: Call<OpenWeatherMainDataClass>,
                response: Response<OpenWeatherMainDataClass>
            ) {
                if(response.body() != null){
                    weatherResponseLiveData!!.postValue(response.body())
                    //state.value = WeatherRepository.State.DONE
                    Log.d("testecidade03mar", ""+response.body()!!.name)

                }else{
                    //state.value = WeatherRepository.State.ERROR
                }
            }

            override fun onFailure(call: Call<OpenWeatherMainDataClass>, t: Throwable) {
                //state.value = WeatherRepository.State.ERROR
                weatherResponseLiveData!!.postValue(null)
                Log.d("testecidade03mar", ""+t.toString())
            }

        })
    }

    override fun getLocationFromViewModel(nomeCidade: String?) {
        TODO("Not yet implemented")
    }



}