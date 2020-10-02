package com.example.weather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weather.model.*
import com.example.weather.viewmodel.ForecastViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class ForecastViewModelTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var weatherBitService: WeatherBitService

    @InjectMocks
    var forecastViewModel = ForecastViewModel()

    private var testSingle: Single<Forecast>? = null

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Before
    fun setUpRxSchedulers() {
        val immediate = object : Scheduler() {
            override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() }, false)
            }
        }

        RxJavaPlugins.setInitIoSchedulerHandler { immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
    }

    @Test
    fun `get forecast success`() {
        val weather = Forecast.Weather("Sunny day")
        val observationData = Forecast.ObservationData("city", 28F, 1002F, 0, 29F, weather, 38F)
        val forecast = Forecast(arrayListOf(observationData))

        testSingle = Single.just(forecast)

        `when`(weatherBitService.getCurrentWeather("Curitiba")).thenReturn(testSingle)

        forecastViewModel.refresh("Curitiba")

        Assert.assertEquals(1, forecastViewModel.forecast.value?.observationData?.size)
        Assert.assertEquals(false, forecastViewModel.forecastLoadError.value)
        Assert.assertEquals(false, forecastViewModel.loading.value)
    }

    @Test
    fun `get forecast failed`() {
        testSingle = Single.error(Throwable())

        `when`(weatherBitService.getCurrentWeather("Curitiba")).thenReturn(testSingle)

        forecastViewModel.refresh("Curitiba")

        Assert.assertEquals(null, forecastViewModel.forecast.value)
        Assert.assertEquals(true, forecastViewModel.forecastLoadError.value)
        Assert.assertEquals(false, forecastViewModel.loading.value)
    }
}