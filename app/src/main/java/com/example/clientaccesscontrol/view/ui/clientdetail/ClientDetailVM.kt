package com.example.clientaccesscontrol.view.ui.clientdetail

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientaccesscontrol.data.preference.UserRepository
import com.example.clientaccesscontrol.data.response.GetAccessResponse
import com.example.clientaccesscontrol.data.response.GetSpeedResponse
import com.example.clientaccesscontrol.data.result.Results
import kotlinx.coroutines.launch

class ClientDetailVM(private val repository: UserRepository) : ViewModel() {

    private val _getAccess = MediatorLiveData<Results<GetAccessResponse>>()
    val getAccess: MutableLiveData<Results<GetAccessResponse>> = _getAccess

    private val _getSpeed = MediatorLiveData<Results<GetSpeedResponse>>()
    val getSpeed: MutableLiveData<Results<GetSpeedResponse>> = _getSpeed

    init {
        viewModelScope.launch {
            repository.getSession().collect { user ->
                user.token.let { token ->
                    _getAccess.addSource(repository.getAccess(token)) { result ->
                        _getAccess.value = result
                    }
                    _getSpeed.addSource(repository.getSpeed(token)) { result ->
                        _getSpeed.value = result
                    }
                }
            }
        }
    }
}