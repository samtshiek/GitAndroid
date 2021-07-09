package com.samandroid.volleypostproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_order_confirm.*

class OrderConfirmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirm)

        init()
    }

    private fun init() {
        button_home.setOnClickListener{
            startActivity(Intent(this, HomeActivity()::class.java))
        }
    }


}