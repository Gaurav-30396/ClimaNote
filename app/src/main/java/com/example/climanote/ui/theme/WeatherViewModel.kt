package com.example.climanote.ui.theme


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climanote.data.CityPreferences
import com.example.climanote.data.remote.RetrofitInstance
import com.example.climanote.domain.WeatherInterpreter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

  class WeatherViewModel(
     private val cityPreferences: CityPreferences
      ) : ViewModel() {

    private val interpreter = WeatherInterpreter()

    private val _uiState =
        MutableStateFlow<WeatherUiState>(WeatherUiState.Idle)
    val uiState: StateFlow<WeatherUiState> = _uiState

    private val API_KEY = "ae0643ff0d6fdcecbf521a0cd17a7e6b"

    init {
        viewModelScope.launch {
            cityPreferences.lastCity
                .collect { city ->
                    if (!city.isNullOrBlank() &&
                        _uiState.value is WeatherUiState.Idle
                    ) {
                        fetchWeather(city)
                    }
                }
        }
    }

    fun fetchWeather(city: String) {

        _uiState.value = WeatherUiState.Loading

        viewModelScope.launch {
            try {
                // ✅ Save city ONCE per user action
                cityPreferences.saveCity(city)

                val response = RetrofitInstance
                    .ApiService
                    .getCurrentWeather(city, API_KEY)

                val insight = interpreter.interpret(response)

                val temperatureText =
                    "${response.main.temp.toInt()}°C "
                val feelslike = "(feels like ${response.main.feels_like.toInt()}°C)"

                _uiState.value = WeatherUiState.Success(
                    city = response.name,
                    temperature = temperatureText,
                    feels_like = feelslike,
                    insight = insight,
                    lastUpdated = "Last updated just now"
                )

            } catch (e: Exception) {
                _uiState.value = WeatherUiState.Error(
                    message = "Unable to fetch weather"
                )
            }
        }
    }
}
