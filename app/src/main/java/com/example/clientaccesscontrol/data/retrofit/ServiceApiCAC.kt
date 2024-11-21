package com.example.clientaccesscontrol.data.retrofit

import com.example.clientaccesscontrol.data.response.GetBTSResponse
import com.example.clientaccesscontrol.data.response.GetChannelWidthResponse
import com.example.clientaccesscontrol.data.response.GetModeResponse
import com.example.clientaccesscontrol.data.response.GetPresharedKeyResponse
import com.example.clientaccesscontrol.data.response.GetRadioResponse
import com.example.clientaccesscontrol.data.response.LoginResponse
import com.example.clientaccesscontrol.data.response.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ServiceApiCAC {
    @FormUrlEncoded
    @POST("user/add")
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("ip_address") ipAddress: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("ip_address") ipAddress: String,
        @Field("username") username: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("bts/")
    suspend fun getBTS(
        @Header("Authorization") token: String
    ): GetBTSResponse

    @GET("radio/")
    suspend fun getRadio(
        @Header("Authorization") token: String
    ): GetRadioResponse

    @GET("mode/")
    suspend fun getMode(
        @Header("Authorization") token: String
    ): GetModeResponse

    @GET("channelWidth/")
    suspend fun getChannelWidth(
        @Header("Authorization") token: String
    ): GetChannelWidthResponse

    @GET("presharedKey/")
    suspend fun getPresharedKey(
        @Header("Authorization") token: String
    ): GetPresharedKeyResponse

}