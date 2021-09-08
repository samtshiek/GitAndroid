package com.samandroid.hilt.api

import com.samandroid.hilt.api.request.LoginRequest
import com.samandroid.hilt.api.response.LoginResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("auth/login")
    suspend fun Login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}