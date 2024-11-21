package com.example.clientaccesscontrol.data.preference

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.clientaccesscontrol.data.response.GetBTSResponse
import com.example.clientaccesscontrol.data.response.GetChannelWidthResponse
import com.example.clientaccesscontrol.data.response.GetModeResponse
import com.example.clientaccesscontrol.data.response.GetPresharedKeyResponse
import com.example.clientaccesscontrol.data.response.GetRadioResponse
import com.example.clientaccesscontrol.data.response.RegisterResponse
import com.example.clientaccesscontrol.data.result.Results
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

        loginResponse.loginResult?.token?.let {
            Log.d("UserRepository", "Saving token: $it")
            saveToken(it)
        }
        loginResponse.loginResult?.ipAddress?.let {
            Log.d("UserRepository", "Saving base URL: $it")
            saveUrlBase(it)
        }
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun getBTS(
        token: String
    ): LiveData<Results<GetBTSResponse>> = liveData {
        emit(Results.Loading)
        try {
            val response = apiServiceCAC.getBTS("Bearer $token")
            emit(Results.Success(response))
        } catch (e: Exception) {
            emit(Results.Error(e.message.toString()))
        }
    }

    suspend fun getRadio(
        token: String
    ): LiveData<Results<GetRadioResponse>> = liveData {
        emit(Results.Loading)
        try {
            val response = apiServiceCAC.getRadio("Bearer $token")
            emit(Results.Success(response))
        } catch (e: Exception) {
            emit(Results.Error(e.message.toString()))
        }
    }

    suspend fun getMode(
        token: String
    ): LiveData<Results<GetModeResponse>> = liveData {
        emit(Results.Loading)
        try {
            val response = apiServiceCAC.getMode("Bearer $token")
            emit(Results.Success(response))
        } catch (e: Exception) {
            emit(Results.Error(e.message.toString()))
        }
    }

    suspend fun getChannelWidth(
        token: String
    ): LiveData<Results<GetChannelWidthResponse>> = liveData {
        emit(Results.Loading)
        try {
            val response = apiServiceCAC.getChannelWidth("Bearer $token")
            emit(Results.Success(response))
        } catch (e: Exception) {
            emit(Results.Error(e.message.toString()))
        }
    }

    suspend fun getPresharedKey(
        token: String
    ): LiveData<Results<GetPresharedKeyResponse>> = liveData {
        emit(Results.Loading)
        try {
            val response = apiServiceCAC.getPresharedKey("Bearer $token")
            emit(Results.Success(response))
        } catch (e: Exception) {
            emit(Results.Error(e.message.toString()))
        }
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