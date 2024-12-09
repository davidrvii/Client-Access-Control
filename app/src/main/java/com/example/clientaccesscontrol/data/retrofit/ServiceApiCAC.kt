package com.example.clientaccesscontrol.data.retrofit

import com.example.clientaccesscontrol.data.response.CreateBTSResponse
import com.example.clientaccesscontrol.data.response.CreateChannelWidthResponse
import com.example.clientaccesscontrol.data.response.CreateModeResponse
import com.example.clientaccesscontrol.data.response.CreatePresharedKeyResponse
import com.example.clientaccesscontrol.data.response.CreateRadioResponse
import com.example.clientaccesscontrol.data.response.DeleteBTSResponse
import com.example.clientaccesscontrol.data.response.DeleteChannelWidthResponse
import com.example.clientaccesscontrol.data.response.DeleteModeResponse
import com.example.clientaccesscontrol.data.response.DeletePresharedKeyResponse
import com.example.clientaccesscontrol.data.response.DeleteRadioResponse
import com.example.clientaccesscontrol.data.response.GetAccessResponse
import com.example.clientaccesscontrol.data.response.GetAllClientResponse
import com.example.clientaccesscontrol.data.response.GetBTSResponse
import com.example.clientaccesscontrol.data.response.GetChannelWidthResponse
import com.example.clientaccesscontrol.data.response.GetClientDetailResponse
import com.example.clientaccesscontrol.data.response.GetModeResponse
import com.example.clientaccesscontrol.data.response.GetPresharedKeyResponse
import com.example.clientaccesscontrol.data.response.GetRadioResponse
import com.example.clientaccesscontrol.data.response.GetSpeedResponse
import com.example.clientaccesscontrol.data.response.LoginResponse
import com.example.clientaccesscontrol.data.response.RegisterResponse
import com.example.clientaccesscontrol.data.response.UpdateClientDetailResponse
import com.example.clientaccesscontrol.data.response.UpdateNetworkResponse
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ServiceApiCAC {
    @FormUrlEncoded
    @POST("user/add")
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("ip_address") ipAddress: String,
    ): RegisterResponse

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("ip_address") ipAddress: String,
        @Field("username") username: String,
        @Field("password") password: String,
    ): LoginResponse

    @FormUrlEncoded
    @POST("bts/add")
    suspend fun createBTS(
        @Header("Authorization") token: String,
        @Field("bts") bts: String,
    ): CreateBTSResponse

    @FormUrlEncoded
    @POST("mode/add")
    suspend fun createMode(
        @Header("Authorization") token: String,
        @Field("mode") mode: String,
    ): CreateModeResponse

    @FormUrlEncoded
    @POST("radio/add")
    suspend fun createRadio(
        @Header("Authorization") token: String,
        @Field("radio") radio: String,
    ): CreateRadioResponse

    @FormUrlEncoded
    @POST("channelWidth/add")
    suspend fun createChannelWidth(
        @Header("Authorization") token: String,
        @Field("channel_width") channelWidth: String,
    ): CreateChannelWidthResponse

    @FormUrlEncoded
    @POST("presharedKey/add")
    suspend fun createPresharedKey(
        @Header("Authorization") token: String,
        @Field("preshared_key") presharedKey: String,
    ): CreatePresharedKeyResponse

    @GET("bts/")
    suspend fun getBTS(
        @Header("Authorization") token: String,
    ): GetBTSResponse

    @GET("radio/")
    suspend fun getRadio(
        @Header("Authorization") token: String,
    ): GetRadioResponse

    @GET("mode/")
    suspend fun getMode(
        @Header("Authorization") token: String,
    ): GetModeResponse

    @GET("channelWidth/")
    suspend fun getChannelWidth(
        @Header("Authorization") token: String,
    ): GetChannelWidthResponse

    @GET("presharedKey/")
    suspend fun getPresharedKey(
        @Header("Authorization") token: String,
    ): GetPresharedKeyResponse

    @DELETE("bts/{id}")
    suspend fun deleteBTS(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ): DeleteBTSResponse

    @DELETE("mode/{id}")
    suspend fun deleteMode(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ): DeleteModeResponse

    @DELETE("radio/{id}")
    suspend fun deleteRadio(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ): DeleteRadioResponse

    @DELETE("channelWidth/{id}")
    suspend fun deleteChannelWidth(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ): DeleteChannelWidthResponse

    @DELETE("presharedKey/{id}")
    suspend fun deletePresharedKey(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ): DeletePresharedKeyResponse

    @GET("access/")
    suspend fun getAccess(
        @Header("Authorization") token: String,
    ): GetAccessResponse

    @GET("speed/")
    suspend fun getSpeed(
        @Header("Authorization") token: String,
    ): GetSpeedResponse

    @GET("client/byUser")
    suspend fun getAllClient(
        @Header("Authorization") token: String,
    ): GetAllClientResponse

    @GET("client/{id}")
    suspend fun getClientDetail(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ): GetClientDetailResponse

    @FormUrlEncoded
    @PATCH("network/{id}")
    suspend fun updateNetwork(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Field("radio_name") radioName: String,
        @Field("frequency") frequency: String,
        @Field("ip_radio") ipRadio: String,
        @Field("ip_address") ipAddress: String,
        @Field("wlan_mac_address") wlanMacAddress: String,
        @Field("ssid") ssid: String,
        @Field("radio_signal") radioSignal: String,
        @Field("ap_location") apLocation: String,
        @Field("fk_radio_id") radio: Int,
        @Field("fk_mode_id") mode: Int,
        @Field("fk_channel_width_id") channelWidth: Int,
        @Field("fk_preshared_key_id") presharedKey: Int,
        @Field("comment") comment: String,
        @Field("password") password: String,
        @Field("fk_bts_id") bts: Int,
    ): UpdateNetworkResponse

    @FormUrlEncoded
    @PATCH("client/{id}")
    suspend fun updateClient(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Field("fk_access_id") access: Int,
        @Field("fk_speed_id") speed: Int,
    ): UpdateClientDetailResponse

//    @DELETE("client/{id}")
//    suspend fun deleteClient(
//        @Header("Authorization") token: String,
//        @Path("id") id: Int
//    ): DeleteClientResponse
}