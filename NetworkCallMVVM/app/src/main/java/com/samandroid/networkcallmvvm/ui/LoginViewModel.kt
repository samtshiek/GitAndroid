package com.samandroid.networkcallmvvm.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samandroid.networkcallmvvm.api.ApiClient
import com.samandroid.networkcallmvvm.data.LoginRequest
import com.samandroid.networkcallmvvm.data.LoginResponse
import retrofit2.Call
import retrofit2.Response

class LoginViewModel:ViewModel() {

    var responseSuccessful = MutableLiveData<Boolean>()
    var responseLiveData = MutableLiveData<String>()

    fun Login(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)
        val apiClientService = ApiClient.getRetrofitUserApi()
        val call = apiClientService.Login(loginRequest).enqueue(object: retrofit2.Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.isSuccessful) {
                    Log.d("abc", "Response is successful, response: " + response.body())
                    responseSuccessful.postValue(true)
                    responseLiveData.postValue("Response succesful")
                }
                else {
                    responseLiveData.postValue("response failed, response: " + response.raw().networkResponse)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("abc", "Response is NOT successful, response: " + t.message)
                responseSuccessful.postValue(false)
                responseLiveData.postValue(t.message)
            }

        })
    }
}