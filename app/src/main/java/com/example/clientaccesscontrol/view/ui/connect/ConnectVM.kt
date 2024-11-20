package com.example.clientaccesscontrol.view.ui.connect

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.clientaccesscontrol.data.preference.UserRepository
import com.example.clientaccesscontrol.data.response.RegisterResponse

class ConnectVM(private val repository: UserRepository) : ViewModel() {

    suspend fun login(ipAddress: String, username: String, password: String) {
        Log.d("ConnectVM", "Login called with username: $username, password: $password, ipAddress: $ipAddress")
        repository.login(ipAddress, username, password)
        Log.d("ConnectVM", "Login successful in repository")
    }

    suspend fun register(username: String, password: String, ipAddress: String): RegisterResponse {
        Log.d("ConnectVM", "Register called with username: $username, password: $password, ipAddress: $ipAddress")
        return repository.register(username, password, ipAddress)

    }
}