package com.example.climanote.domain

import com.example.climanote.data.model.WeatherResponse

class WeatherInterpreter {
    fun interpret(response: WeatherResponse): WeatherInsight{
        val feelsLikeTemp = response.main.feels_like
        val condition = response.weather.firstOrNull()?.main?:"Clear"
        val rainAmount = response.rain?.lastOneHour?:0.0

        val summary: String
        val action: String

        when{
            rainAmount>0-> {
                summary = "Rain expected today"
                action = "Carry an umbrella"
            }
            condition == "Clear" && feelsLikeTemp >= 30->{
                summary = "Hot and sunny weather."
                action = "Stay hydrated and avoid afternoon heat"
            }
            condition == "clouds" && feelsLikeTemp < 30 ->{
                summary = "Cloudy but comfortable weather."
                action = "Good day to go outside."
            }
            feelsLikeTemp < 15 -> {
                summary = "Cool weather conditions."
                action = "Carry a light jacket."
            }
            else -> {
                summary = "Pleasant weather throughout the day."
                action = "Enjoy your day outdoors."
            }
        }
        return WeatherInsight(
            summary = summary,
            action = action
        )

    }
}