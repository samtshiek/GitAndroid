package com.samandroid.networkcallmvvm.api

import com.samandroid.networkcallmvvm.data.LoginRequest
import com.samandroid.networkcallmvvm.data.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("auth/login")
    fun Login(@Body loginRequest: LoginRequest): Call<LoginResponse>
}