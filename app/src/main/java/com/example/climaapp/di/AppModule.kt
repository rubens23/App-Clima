package com.example.climaapp.di

import com.example.climaapp.data.remote.apiclima.OpenWeatherInterface
import com.example.climaapp.data.remote.apiclima.repositories.WeatherRepository
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
    fun provideClimateApi(): OpenWeatherInterface{
        return Retrofit.Builder()
            .baseUrl(WeatherRepository.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenWeatherInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideWeatherRepository(
        api: OpenWeatherInterface
    ) = WeatherRepository(api)
}