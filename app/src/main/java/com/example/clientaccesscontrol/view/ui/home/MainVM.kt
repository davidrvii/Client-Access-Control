package com.example.clientaccesscontrol.view.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.clientaccesscontrol.data.preference.UserModel
import com.example.clientaccesscontrol.data.preference.UserRepository
import com.example.clientaccesscontrol.data.response.GetAllClientResponse
import com.example.clientaccesscontrol.data.result.Results
import kotlinx.coroutines.launch

class MainVM(private val repository: UserRepository) : ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    private val _getAllClient = MediatorLiveData<Results<GetAllClientResponse>>()
    val getAllClient: LiveData<Results<GetAllClientResponse>> = _getAllClient

    init {
        viewModelScope.launch {
            repository.getSession().collect { user ->
                user.token.let { token ->
                    _getAllClient.addSource(repository.getAllClient(token)) { result ->
                        _getAllClient.value = result
                    }
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}