package com.example.weather.di

import com.example.weather.model.WeatherBitService
import com.example.weather.model.WeatherbitAPI
import com.example.weather.utils.ApiInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {
    private val WEATHERBIT_BASE_URL = "https://api.weatherbit.io/v2.0/"

    @Provides
    fun provideWeatherBitApi(): WeatherbitAPI {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(ApiInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl(WEATHERBIT_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(WeatherbitAPI::class.java)
    }

    @Provides
    fun providerWeatherBitService(): WeatherBitService {
        return WeatherBitService()
    }
}