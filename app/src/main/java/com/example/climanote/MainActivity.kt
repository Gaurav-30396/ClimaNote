package com.example.climanote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.climanote.data.CityPreferences
import com.example.climanote.ui.theme.ClimaNoteTheme
import com.example.climanote.ui.theme.WeatherScreen
import com.example.climanote.ui.theme.WeatherViewModel
import com.example.climanote.ui.theme.WeatherViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Create CityPreferences using applicationContext
        val cityPreferences = CityPreferences(applicationContext)

        // Create ViewModelFactory
        val factory = WeatherViewModelFactory(cityPreferences)
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: WeatherViewModel =
                viewModel(factory = factory)

            ClimaNoteTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WeatherScreen(viewModel = viewModel )
                }
            }
        }
    }
}

