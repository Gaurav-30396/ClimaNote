package com.example.climanote.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.climanote.R

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {

    val uiState by viewModel.uiState.collectAsState()
    var showCityDialog by remember { mutableStateOf(false) }

    when (uiState) {

        is WeatherUiState.Idle -> {
            IdleScreen { city ->
                viewModel.fetchWeather(city)
            }
        }

        is WeatherUiState.Loading -> {
            LoadingScreen()
        }

        is WeatherUiState.Success -> {
            SuccessScreen(
                data = uiState as WeatherUiState.Success,
                onChangeCity = { showCityDialog = true }
            )
        }

        is WeatherUiState.Error -> {
            ErrorScreen(
                message = (uiState as WeatherUiState.Error).message,
                onRetry = {
                    showCityDialog = true
                }
            )
        }
    }

    if (showCityDialog) {
        CityInputDialog(
            onCitySelected = { city ->
                viewModel.fetchWeather(city)
                showCityDialog = false
            },
            onDismiss = { showCityDialog = false }
        )
    }
}

@Composable
fun CityInputDialog(
    onCitySelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var city by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Enter City Name") },
        text = {
            TextField(
                value = city,
                onValueChange = { city = it },
                singleLine = true,
                placeholder = { Text("Delhi") }
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    if (city.isNotBlank()) {
                        onCitySelected(city.trim())
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5F8C8B),
                    contentColor = Color.White
                )
            ) {
                Text("Done")
            }
        }
    )
}

@Composable
fun IdleScreen(onSelectCity: (String) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_add_location_24),
            contentDescription = null,
            modifier = Modifier.size(128.dp),
            tint = Color(0xFF5F8C8B)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Get today's weather in one line",
            fontSize = 24.sp,
            color = Color(0xFF5F8C8B),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { showDialog = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF5F8C8B),
                contentColor = Color.White
            )
        ) {
            Text("Select City")
        }
    }

    if (showDialog) {
        CityInputDialog(
            onCitySelected = {
                onSelectCity(it)
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }
}

@Composable
fun SuccessScreen(
    data: WeatherUiState.Success,
    onChangeCity: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = Color(0xFF5F8C8B)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(data.city, fontWeight = FontWeight.SemiBold)
            }

            Text(
                text = "Change city",
                color = Color(0xFF5F8C8B),
                modifier = Modifier.clickable { onChangeCity() }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(364.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFA)),

        ) {
            Column(
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.baseline_cloud_24),
                    contentDescription = null,
                    tint = Color(0xFF5F8C8B),
                    modifier = Modifier.size(56.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = data.temperature,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF5F8C8B)
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = data.feels_like,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF5F8C8B)
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = data.insight.summary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF5F8C8B)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = data.insight.action,
                    fontSize = 14.sp,
                    color = Color(0xFF5F8C8B),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = data.lastUpdated,
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(color = Color(0xFF5F8C8B))
            Spacer(modifier = Modifier.height(8.dp))
            Text("Fetching latest weather...")
        }
    }
}

@Composable
fun ErrorScreen(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(message)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Try Again")
        }
    }
}

