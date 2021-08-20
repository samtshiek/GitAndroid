package com.samandroid.networkcallmvvm.api

import com.google.gson.Gson
import com.samandroid.networkcallmvvm.utils.Config
import okhttp3.Interceptor
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    fun getRetrofit(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }).build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(Config.BASE_URL)
            .build()

        return retrofit


    }

    fun getRetrofitUserApi() = getRetrofit().create(UserApi::class.java)
}