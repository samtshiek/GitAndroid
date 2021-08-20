package com.samandroid.networkcallmvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.samandroid.networkcallmvvm.ui.LoginViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    fun init() {
        button_login.setOnClickListener {
            viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
            val email = edit_text_email.text.toString()
            val password = edit_text_password.text.toString()

            viewModel.Login(email, password)

            viewModel.responseSuccessful.observe(this) {
                Log.d("abc", "ResponseSuccessful livedata:" + it)
            }

            viewModel.responseLiveData.observe(this) {
                text_view_failed_message.text = it
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }



    }
}