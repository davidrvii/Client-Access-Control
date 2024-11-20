package com.example.clientaccesscontrol.data.preference

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {
    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            val token = preferences[TOKEN_KEY] ?: ""
            Log.d("UserPreferences", "Token Retrieved in Session: $token")
            UserModel(
                preferences[IP_ADDRESS_KEY] ?: "",
                preferences[TOKEN_KEY] ?: "",
                preferences[IS_LOGIN_KEY] ?: false
            )
        }
    }

    suspend fun saveToken(token: String?) {
        if (token != null) {
            dataStore.edit { preferences ->
                preferences[TOKEN_KEY] = token
                preferences[IS_LOGIN_KEY] = true
                Log.d("UserPreferences", "Token Saved in Session: $token")
            }
        }
    }

    suspend fun saveBaseUrl(baseUrl: String) {
        dataStore.edit { preferences ->
            preferences[BASE_URL_KEY] = baseUrl
            Log.d("UserPreferences", "Base URL Saved in Session: $baseUrl")
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    fun getBaseUrl(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[BASE_URL_KEY] ?: "http://192.168.203.162/"
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val BASE_URL_KEY = stringPreferencesKey("base_url")
        private val IP_ADDRESS_KEY = stringPreferencesKey("ip_address")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}