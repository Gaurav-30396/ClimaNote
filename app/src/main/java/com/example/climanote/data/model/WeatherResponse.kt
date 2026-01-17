package com.example.climanote.data.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val name:String,
    val main: Main,
    val weather : List<Weather>,
    val rain: Rain?,
    val dt: Long
)
data class Main(
    val temp: Double,
    val feels_like: Double
)
data class Weather(
    val main: String,
    val description: String
)
data class Rain(
    @SerializedName("1h")
    val lastOneHour: Double?
)