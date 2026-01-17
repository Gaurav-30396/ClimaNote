package com.example.climanote.data



import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("city_prefs")

class CityPreferences(private val context: Context) {

    private val CITY_KEY = stringPreferencesKey("last_city")

    val lastCity: Flow<String?> = context.dataStore.data
        .map { prefs -> prefs[CITY_KEY] }

    suspend fun saveCity(city: String) {
        context.dataStore.edit { prefs ->
            prefs[CITY_KEY] = city
        }
    }
}
