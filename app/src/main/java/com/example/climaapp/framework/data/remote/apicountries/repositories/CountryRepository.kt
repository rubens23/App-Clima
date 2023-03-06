package com.example.climaapp.framework.data.remote.apicountries.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import retrofit2.Callback
import com.example.climaapp.framework.data.remote.apicountries.RestCountriesInterface
import com.example.climaapp.framework.data.remote.apicountries.models.CountryMainModel

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory

class CountryRepository {
    enum class State{
        DOING, DONE, ERROR
    }

    companion object{
        const val BASE_URL = "https://restcountries.com/v3.1/"
    }

    private lateinit var countryService: RestCountriesInterface
    private var countryResponseLiveData: MutableLiveData<List<CountryMainModel>>?
    private val state: MutableLiveData<State> = MutableLiveData<State>()

    init{
        countryResponseLiveData = MutableLiveData<List<CountryMainModel>>()

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        countryService = retrofit2.Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RestCountriesInterface::class.java)
    }

    fun getCountry(){
        state.value = State.DOING
        countryService.getAllCountries().enqueue(object: Callback<List<CountryMainModel>>{
            override fun onResponse(
                call: Call<List<CountryMainModel>>,
                response: Response<List<CountryMainModel>>
            ) {
                if(response.body() != null){
                    countryResponseLiveData!!.postValue(response.body())
                    state.value = State.DONE
                    Log.d("testecountry", ""+response.body()!!)

                }else{
                    state.value = State.ERROR
                }
            }

            override fun onFailure(call: Call<List<CountryMainModel>>, t: Throwable) {
                state.value = State.ERROR
                countryResponseLiveData!!.postValue(null)
                Log.d("testecountry", ""+t.toString())
            }

        })

    }

    fun getCountryResponse(): MutableLiveData<List<CountryMainModel>>? = countryResponseLiveData

    fun getState(): MutableLiveData<State> = this.state


}
