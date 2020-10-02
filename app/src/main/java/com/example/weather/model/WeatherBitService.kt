package com.example.weather.model

import com.example.weather.di.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject

class WeatherBitService {
    @Inject
    lateinit var api: WeatherbitAPI

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getCurrentWeather(city: String): Single<Forecast> {
        return api.getCurrentWeather(city)
    }
}