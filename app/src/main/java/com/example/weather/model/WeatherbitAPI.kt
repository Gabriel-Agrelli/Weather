package com.example.weather.model

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherbitAPI {
    @GET("current")
    fun getCurrentWeather(@Query("city") city: String): Single<Forecast>
}