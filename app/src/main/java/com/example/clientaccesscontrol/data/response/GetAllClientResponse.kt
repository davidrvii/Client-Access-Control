package com.example.clientaccesscontrol.data.response

import com.google.gson.annotations.SerializedName

data class GetAllClientResponse(

	@field:SerializedName("allClient")
	val allClient: List<AllClientItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("statusCode")
	val statusCode: Int? = null
)

data class AllClientItem(

	@field:SerializedName("internet_access")
	val internetAccess: String? = null,

	@field:SerializedName("client_id")
	val clientId: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("ip_address")
	val ipAddress: String? = null
)
