package com.example.climanote.ui.theme



import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.climanote.data.CityPreferences
//import com.example.climanote.data.preferen..ces.CityPreferences

class WeatherViewModelFactory(
    private val cityPreferences: CityPreferences
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WeatherViewModel(cityPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
