package com.example.clientaccesscontrol.data.preference

import android.util.Log
import com.example.clientaccesscontrol.data.response.RegisterResponse
import com.example.clientaccesscontrol.data.retrofit.ServiceApiCAC
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(
    private val userPreference: UserPreference,
    /*private val apiServiceMikrotik: ServiceApiMikrotik,*/
    private val apiServiceCAC: ServiceApiCAC
) {

    private suspend fun saveToken(token: String) {
        Log.d("UserRepository", "Token Saved in Session: $token")
        userPreference.saveToken(token)
    }

    private suspend fun saveUrlBase(baseUrl: String) {
        Log.d("UserRepository", "Base URL Saved in Session: $baseUrl")
        userPreference.saveBaseUrl(baseUrl)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun register(
        username: String,
        password: String,
        ipAddress: String
    ): RegisterResponse {
        Log.d("UserRepository", "Starting register with username: $username, ipAddress: $ipAddress")
        return apiServiceCAC.register(username, password, ipAddress)
    }

    suspend fun login(
        ipAddress: String,
        username: String,
        password: String
    ) {
        Log.d("UserRepository", "Starting login with username: $username, ipAddress: $ipAddress")
        val loginResponse = apiServiceCAC.login(ipAddress, username, password)
        Log.d("UserRepository", "Login response: $loginResponse")
        if (loginResponse.message == "Login Success") {
            loginResponse.token.let {
                Log.d("UserRepository", "Saving token: $it")
                saveToken(it)
            }
            loginResponse.ipAddress.let {
                Log.d("UserRepository", "Saving base URL: $it")
                saveUrlBase(it)
            }
        } else {
            Log.e("UserRepository", "Login failed with message: ${loginResponse.message}")
            throw Exception("Login failed: ${loginResponse.message}")
        }
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            /*apiServiceMikrotik: ServiceApiMikrotik,*/
            apiServiceCAC: ServiceApiCAC
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, /*apiServiceMikrotik,*/ apiServiceCAC)
            }.also { instance = it }
    }
}