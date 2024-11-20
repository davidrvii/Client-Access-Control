package com.example.clientaccesscontrol.injection

import android.content.Context
import com.example.clientaccesscontrol.data.preference.UserPreference
import com.example.clientaccesscontrol.data.preference.UserRepository
import com.example.clientaccesscontrol.data.preference.dataStore
import com.example.clientaccesscontrol.data.retrofit.ConfigApi

object Data {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        /*val apiServiceMikrotik = ConfigApi.getApiServiceMikrotik(context)*/
        val apiServiceCAC = ConfigApi.getApiServiceCAC()
        return UserRepository.getInstance(pref, /*apiServiceMikrotik,*/ apiServiceCAC)
    }
}