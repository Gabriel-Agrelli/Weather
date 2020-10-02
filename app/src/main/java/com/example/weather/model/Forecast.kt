package com.example.weather.model

import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("data")
    val observationData: List<ObservationData>?
) {
    data class ObservationData(
        @SerializedName("city_name")
        val city: String?,
        @SerializedName("rh")
        val humidity: Float?,
        @SerializedName("pres")
        val pressure: Float?,
        @SerializedName("clouds")
        val uv: Int?,
        @SerializedName("wind_spd")
        val windSpeed: Float?,
        @SerializedName("weather")
        val weather: Weather?,
        @SerializedName("temp")
        val temperature: Float?
    )

    data class Weather(
        @SerializedName("description")
        val description: String?
    )
}
