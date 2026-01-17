package com.example.climanote.ui.theme

import com.example.climanote.data.model.WeatherResponse
import com.example.climanote.domain.WeatherInsight

sealed class WeatherUiState {
    object Idle: WeatherUiState()
    object Loading: WeatherUiState()
    data class Success(
        val city: String,
        val temperature: String,
        val feels_like: String,
        val insight: WeatherInsight,
        val lastUpdated: String
    ) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
}