package com.example.clientaccesscontrol.data.retrofit

import com.example.clientaccesscontrol.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ConfigApi {
    private fun retrofitInstance(baseUrl: String): Retrofit {
        val loggingInterceptor = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun getApiServiceCAC(): ServiceApiCAC {
        val baseUrl = "https://clientaccesscontrol.up.railway.app/"
        return retrofitInstance(baseUrl).create(ServiceApiCAC::class.java)
    }

    fun getApiServiceMikrotik(baseUrl: String): ServiceApiMikrotik {
        return retrofitInstance(baseUrl).create(ServiceApiMikrotik::class.java)
    }
}