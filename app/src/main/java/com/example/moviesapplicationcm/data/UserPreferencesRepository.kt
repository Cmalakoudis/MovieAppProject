package com.example.moviesapplicationcm.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey

class  UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val USERNAME = stringPreferencesKey("username")
        const val TAG = "UserPreferencesRepo"
    }

    val preferences: Flow<AppUIState.MovieAppUiState> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            val isDarkTheme = preferences[IS_DARK_THEME] ?: false
            val isLoggedIn = preferences[IS_LOGGED_IN] ?: false
            val username = preferences[USERNAME] ?: ""
            AppUIState.MovieAppUiState(darkTheme = isDarkTheme,isLoggedIn = isLoggedIn, userName = username)
        }

    suspend fun savePreferences(isDarkTheme: Boolean, username: String, isLoggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_DARK_THEME] = isDarkTheme
            preferences[IS_LOGGED_IN] = isLoggedIn
            preferences[USERNAME] = username
        }
    }
}