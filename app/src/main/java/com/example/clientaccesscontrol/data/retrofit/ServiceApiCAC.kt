package com.example.clientaccesscontrol.data.retrofit

import com.example.clientaccesscontrol.data.response.LoginResponse
import com.example.clientaccesscontrol.data.response.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
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
}