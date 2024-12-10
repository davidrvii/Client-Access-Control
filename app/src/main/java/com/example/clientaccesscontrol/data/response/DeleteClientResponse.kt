package com.example.clientaccesscontrol.data.response

import com.google.gson.annotations.SerializedName

data class DeleteClientResponse(

	@field:SerializedName("clientId")
	val clientId: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("statusCode")
	val statusCode: Int? = null
)
