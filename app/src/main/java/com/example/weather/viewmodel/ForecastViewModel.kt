package com.example.weather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.di.DaggerApiComponent
import com.example.weather.model.Forecast
import com.example.weather.model.WeatherBitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ForecastViewModel : ViewModel() {
    @Inject
    lateinit var weatherBitService: WeatherBitService

    init {
        DaggerApiComponent.create().inject(this)
    }

    private val disposable = CompositeDisposable()

    val forecast = MutableLiveData<Forecast>()
    val forecastLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh(city: String) {
        fetchCurrentWeather(city)
    }

    private fun fetchCurrentWeather(city: String) {
        loading.value = true
        disposable.add(
            weatherBitService.getCurrentWeather(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Forecast>() {
                    override fun onSuccess(value: Forecast) {
                        forecast.value = value
                        forecastLoadError.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        forecastLoadError.value = true
                        loading.value = false
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}