package com.example.clientaccesscontrol.data.response

import com.google.gson.annotations.SerializedName

data class CreateChannelWidthResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("newChannelWidth")
	val newChannelWidth: NewChannelWidth? = null,

	@field:SerializedName("statusCode")
	val statusCode: Int? = null
)

data class NewChannelWidth(

	@field:SerializedName("channelWidth")
	val channelWidth: String? = null
)
