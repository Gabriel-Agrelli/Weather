package com.example.weather.di

import com.example.weather.model.WeatherBitService
import com.example.weather.viewmodel.ForecastViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(service: WeatherBitService)
    fun inject(viewModel: ForecastViewModel)
}