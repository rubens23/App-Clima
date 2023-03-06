package com.example.climaapp.framework.di

import com.example.climaapp.framework.data.RetrofitDataSource
import com.example.core2.data.remote.apiclima.OpenWeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideClimateApi(): OpenWeatherApi {
        return Retrofit.Builder()
            .baseUrl(RetrofitDataSource.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenWeatherApi::class.java)
    }

    @Singleton
    @Provides
    fun provideWeatherRepository(
        api: OpenWeatherApi
    ) = com.example.core2.repository.WeatherRepositories.WeatherRepository(RetrofitDataSource(api))

//    @Singleton
//    @Provides
//    fun provideCoreWeatherRepository(
//        weatherRepository: com.example.core2.repository.WeatherRepositories.WeatherRepository,
//        api: OpenWeatherApi
//    ) = com.example.core2.repository.WeatherRepositories.WeatherRepository(RetrofitDataSource(api))
}