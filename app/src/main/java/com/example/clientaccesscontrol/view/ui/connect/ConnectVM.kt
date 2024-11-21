package com.example.clientaccesscontrol.view.ui.connect

import androidx.lifecycle.ViewModel
import com.example.clientaccesscontrol.data.preference.UserRepository
import com.example.clientaccesscontrol.data.response.RegisterResponse

class ConnectVM(private val repository: UserRepository) : ViewModel() {

    suspend fun login(ipAddress: String, username: String, password: String) {
        repository.login(ipAddress, username, password)
    }

    suspend fun register(username: String, password: String, ipAddress: String): RegisterResponse {
        return repository.register(username, password, ipAddress)

    }
}