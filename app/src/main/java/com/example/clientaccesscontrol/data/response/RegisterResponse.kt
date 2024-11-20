package com.example.clientaccesscontrol.data.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("ip_address")
	val ipAddress: String,

	@field:SerializedName("username")
	val username: String
)
