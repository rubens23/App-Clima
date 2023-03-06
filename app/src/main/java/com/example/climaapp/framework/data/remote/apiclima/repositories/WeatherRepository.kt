package com.example.climaapp.framework.data.remote.apiclima.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.core2.data.remote.apiclima.OpenWeatherApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/**
 * essa classe vai ser excluida
 */


class WeatherRepository @Inject constructor(
    private val openWeatherApi: OpenWeatherApi
){

    enum class State{
        DOING, DONE, ERROR
    }

    companion object{
        const val BASE_URL = "https://api.openweathermap.org/"
        const val API_KEY = "54454b2259a59ae51ffe392ca35d653c"
    }





    private val state: MutableLiveData<State> = MutableLiveData<State>()





    /*
    private fun getWeather(cidade: String) {
        state.value = State.DOING
        openWeatherApi.getCurrentWeatherByCity(cidade, API_KEY).enqueue(object:
            Callback<OpenWeatherMainDataClass>{
            override fun onResponse(
                call: Call<OpenWeatherMainDataClass>,
                response: Response<OpenWeatherMainDataClass>
            ) {
                if(response.body() != null){
                    weatherResponseLiveData!!.postValue(response.body())
                    state.value = State.DONE
                    Log.d("testecidade", ""+response.body()!!.name)

                }else{
                    state.value = State.ERROR
                }
            }

            override fun onFailure(call: Call<OpenWeatherMainDataClass>, t: Throwable) {
                state.value = State.ERROR
                weatherResponseLiveData!!.postValue(null)
                Log.d("testecidade", ""+t.toString())
            }

        })
        Log.d("testecidade", cidade)
    }

     */

    fun getLocationFromViewModel(nomeCidade: String?) {
        if(nomeCidade != null){
            //getWeather(nomeCidade)
        }


    }

    fun getCapitalFromCapitalViewModel(nomeCidade: String?){
        if(nomeCidade != null){
            //getWeather(nomeCidade)
        }
    }


    fun getState(): MutableLiveData<State> = this.state








}