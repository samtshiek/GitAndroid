package com.samandroid.hilt.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samandroid.hilt.api.ApiClient
import com.samandroid.hilt.api.request.LoginRequest
import com.samandroid.hilt.api.response.LoginResponse
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class LoginViewModel @Inject constructor(): ViewModel() {
    var isSuccessful = MutableLiveData<Boolean>()

    fun Login(username: String, password: String) {
        val loginRequest = LoginRequest(username, password)
        //WITH CALL ADAPTER
        /*val response = ApiClient.getRetrofitApiService().Login(loginRequest).enqueue(object: retrofit2.Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    isSuccessful.postValue(true)
                }
                else {
                    isSuccessful.postValue(false)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("abc", "Exception: ${t.message}")
                isSuccessful.postValue(false)
            }
        })*/

        //WITH RESPONSE ADAPTER
        viewModelScope.launch(IO) {
            val response = ApiClient.getRetrofitApiService().Login(loginRequest)
            if (response.isSuccessful) {
                isSuccessful.postValue(true)
            }
            else {
                isSuccessful.postValue(false)
            }
        }
    }
}