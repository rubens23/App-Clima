package com.example.climaapp.data.remote.apicountries.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import retrofit2.Callback
import com.example.climaapp.data.remote.apicountries.RestCountriesInterface
import com.example.climaapp.data.remote.apicountries.models.CountryMainModel
import com.example.climaapp.tutorialteste.Task
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
    private val state: MutableLiveData<CountryRepository.State> = MutableLiveData<CountryRepository.State>()

    init{
        countryResponseLiveData = MutableLiveData<List<CountryMainModel>>()

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        countryService = retrofit2.Retrofit.Builder()
            .baseUrl(CountryRepository.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RestCountriesInterface::class.java)
    }

    fun getCountry(){
        state.value = CountryRepository.State.DOING
        countryService.getAllCountries().enqueue(object: Callback<List<CountryMainModel>>{
            override fun onResponse(
                call: Call<List<CountryMainModel>>,
                response: Response<List<CountryMainModel>>
            ) {
                if(response.body() != null){
                    countryResponseLiveData!!.postValue(response.body())
                    state.value = CountryRepository.State.DONE
                    Log.d("testecountry", ""+response.body()!!)

                }else{
                    state.value = CountryRepository.State.ERROR
                }
            }

            override fun onFailure(call: Call<List<CountryMainModel>>, t: Throwable) {
                state.value = CountryRepository.State.ERROR
                countryResponseLiveData!!.postValue(null)
                Log.d("testecountry", ""+t.toString())
            }

        })

    }

    fun getCountryResponse(): MutableLiveData<List<CountryMainModel>>? = countryResponseLiveData

    fun getState(): MutableLiveData<CountryRepository.State> = this.state

    //função para treinar testes:
    internal fun getActiveAndCompleteStats(tasks: List<Task>): StatsResult{

        val totalTasks = tasks!!.size
        val numberOfActiveTasks = tasks.count{
            it.isActive
        }
        val activePercent = 100 * numberOfActiveTasks / totalTasks
        val completePercent = 100 * (totalTasks - numberOfActiveTasks) / totalTasks

        return StatsResult(
            activeTasksPercent = activePercent.toFloat(),
            completedTasksPercent = completePercent.toFloat()

        )
    }
}

//data class para treinar testes:
data class StatsResult(
    val activeTasksPercent: Float,
    val completedTasksPercent: Float
)