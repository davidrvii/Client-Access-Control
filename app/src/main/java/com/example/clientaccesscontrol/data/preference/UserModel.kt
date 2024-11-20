package com.example.clientaccesscontrol.data.preference

data class UserModel(
    val ipAdress: String,
    val token: String,
    val isLogin: Boolean = false
)