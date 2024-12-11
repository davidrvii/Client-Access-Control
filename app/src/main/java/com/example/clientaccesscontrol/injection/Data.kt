package com.example.clientaccesscontrol.injection

import android.content.Context
import com.example.clientaccesscontrol.data.preference.UserPreference
import com.example.clientaccesscontrol.data.preference.Repository
import com.example.clientaccesscontrol.data.preference.dataStore
import com.example.clientaccesscontrol.data.retrofit.ConfigApi

object Data {
    fun provideCACRepository(context: Context): Repository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiServiceCAC = ConfigApi.getApiServiceCAC()
        val baseUrl = pref.getBaseUrl()
        val apiServiceMikrotik = ConfigApi.getApiServiceMikrotik(baseUrl)
        return Repository.getInstance(pref, apiServiceCAC, apiServiceMikrotik)
    }
}