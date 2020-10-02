package com.example.weather.utils

import com.example.weather.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val httpUrl = originalRequest.url

        val newHttpUrl =
            httpUrl.newBuilder()
                .addQueryParameter("key", BuildConfig.WeatherBitApiKey)
                .addQueryParameter("lang", "pt")
                .addQueryParameter("units", "M")
                .build()

        val requestBuilder = originalRequest.newBuilder().url(newHttpUrl)
        val request = requestBuilder.build()

        return chain.proceed(request)
    }
}