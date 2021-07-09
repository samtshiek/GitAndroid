package com.samandroid.volleypostproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        init()
    }
    private  fun init() {
        button_register.setOnClickListener {
            var name = edit_text_name.text.toString()
            var email = edit_text_email.text.toString()
            var password = edit_text_password.text.toString()
            var mobile = edit_text_mobile.text.toString()

            var jsonObject = JSONObject()
            jsonObject.put("firstName", name)
            jsonObject.put("email", email)
            jsonObject.put("mobile", mobile)
            jsonObject.put("password", password)

            var requestQueue = Volley.newRequestQueue(this)
            var request = JsonObjectRequest(
                Request.Method.POST,
                "http://apolis-grocery.herokuapp.com/api/auth/register",
                jsonObject,
                Response.Listener {
                    Log.d("abc", it.toString())
                },
                Response.ErrorListener {
                    Log.e("abc", it.message.toString())
                }
            )
            requestQueue.add(request)
        }
    }
}