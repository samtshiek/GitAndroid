package com.samandroid.volleypostproject.Models

data class LoginResponse(
    val token: String,
    val user: User
)

data class User(
    val _id: String,
    val email: String,
    val firstName: String,
    val mobile: String,
    val password: String
)