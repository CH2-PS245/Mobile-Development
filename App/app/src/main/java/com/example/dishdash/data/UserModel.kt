package com.example.dishdash.data

data class UserModel(
    val email : String,
    val accessToken : String,
    val refreshToken : String,
    val isLogin : Boolean = false
)