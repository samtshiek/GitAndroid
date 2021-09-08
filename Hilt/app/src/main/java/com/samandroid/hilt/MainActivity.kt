package com.samandroid.hilt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.samandroid.hilt.databinding.ActivityMainBinding
import com.samandroid.hilt.ui.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var car: MyCar
    @Inject
    lateinit var loginViewModel: LoginViewModel
    lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        setUpObservers()
        setUpEvents()

        viewBinding.textView.text = car.make
    }

    fun setUpObservers() {
        loginViewModel.isSuccessful.observe(this) {
            viewBinding.textView.text = it.toString()
        }
    }

    fun setUpEvents() {
        viewBinding.buttonLogin.setOnClickListener {
            val username = viewBinding.editTextEmail.text.toString()
            val password = viewBinding.editTextPassword.text.toString()

            loginViewModel.Login(username,password)
        }
    }
}