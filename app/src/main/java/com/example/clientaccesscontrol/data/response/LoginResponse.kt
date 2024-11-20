package com.example.clientaccesscontrol.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("ip_address")
	val ipAddress: String,

	@field:SerializedName("token")
	val token: String
)
