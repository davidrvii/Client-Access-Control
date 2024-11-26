package com.example.clientaccesscontrol.view.ui.newclientrouter

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientaccesscontrol.data.preference.UserRepository
import com.example.clientaccesscontrol.data.response.GetBTSResponse
import com.example.clientaccesscontrol.data.response.GetChannelWidthResponse
import com.example.clientaccesscontrol.data.response.GetModeResponse
import com.example.clientaccesscontrol.data.response.GetPresharedKeyResponse
import com.example.clientaccesscontrol.data.response.GetRadioResponse
import com.example.clientaccesscontrol.data.result.Results
import kotlinx.coroutines.launch

class NewClientRouterVM(private val repository: UserRepository) : ViewModel() {
    //BTS
    private val _getBTS = MediatorLiveData<Results<GetBTSResponse>>()
    val getBTS: MutableLiveData<Results<GetBTSResponse>> = _getBTS

    //Radio
    private val _getRadio = MediatorLiveData<Results<GetRadioResponse>>()
    val getRadio: MutableLiveData<Results<GetRadioResponse>> = _getRadio

    //Mode
    private val _getMode = MediatorLiveData<Results<GetModeResponse>>()
    val getMode: MutableLiveData<Results<GetModeResponse>> = _getMode

    //Preshared Key
    private val _getPresharedKey = MediatorLiveData<Results<GetPresharedKeyResponse>>()
    val getPresharedKey: MutableLiveData<Results<GetPresharedKeyResponse>> = _getPresharedKey

    //Channel Width
    private val _getChanelWidth = MediatorLiveData<Results<GetChannelWidthResponse>>()
    val getChannelWidth: MutableLiveData<Results<GetChannelWidthResponse>> = _getChanelWidth

    init {
        viewModelScope.launch {
            repository.getSession().collect { user ->
                user.token.let { token ->
                    _getBTS.addSource(repository.getBTS(token)) { result ->
                        _getBTS.value = result
                    }
                    _getRadio.addSource(repository.getRadio(token)) { result ->
                        _getRadio.value = result
                    }
                    _getMode.addSource(repository.getMode(token)) { result ->
                        _getMode.value = result
                    }
                    _getPresharedKey.addSource(repository.getPresharedKey(token)) { result ->
                        _getPresharedKey.value = result
                    }
                    _getChanelWidth.addSource(repository.getChannelWidth(token)) { result ->
                        _getChanelWidth.value = result
                    }
                }
            }
        }
    }
}