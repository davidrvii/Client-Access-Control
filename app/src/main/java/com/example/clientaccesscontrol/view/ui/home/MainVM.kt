package com.example.clientaccesscontrol.view.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.clientaccesscontrol.data.preference.UserModel
import com.example.clientaccesscontrol.data.preference.UserRepository
import kotlinx.coroutines.launch

class MainVM(private val repository: UserRepository) : ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}