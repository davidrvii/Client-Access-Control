package com.example.clientaccesscontrol.data.retrofit

import com.example.clientaccesscontrol.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ConfigApi {
    private const val BASE_URL_CAC = "https://clientaccesscontrol.up.railway.app/"

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
        return retrofitInstance(BASE_URL_CAC).create(ServiceApiCAC::class.java)
    }

//    fun getApiServiceMikrotik(context: Context): ServiceApiMikrotik {
//        val dataStore: DataStore<Preferences> = context.dataStore
//        val userPreference = UserPreference.getInstance(dataStore)
//        var baseUrl = "http://192.168.203.162/"
//        runBlocking {
//            userPreference.getBaseUrl().collect {
//                baseUrl = it
//            }
//        }
//        return retrofitInstance(baseUrl).create(ServiceApiMikrotik::class.java)
//    }
}