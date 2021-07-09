package com.samandroid.volleypostproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.samandroid.volleypostproject.Models.LoginResponse
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONArray
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    val username: String? = null
    val token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    private fun init() {

        button_login.setOnClickListener{
            progress_bar.visibility = View.VISIBLE
            var email = edit_text_email.text.toString()
            var password = edit_text_password.text.toString()

            var jsonObject = JSONObject()
            jsonObject.put("email", email)
            jsonObject.put("password", password)

            val url = "http://apolis-grocery.herokuapp.com/api/auth/login"
            var requestQueue = Volley.newRequestQueue(this)
            var request = JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonObject,
                Response.Listener {
                    progress_bar.visibility = View.GONE
                    var jsonObject = it
                    var token = jsonObject.getString("token")
                    var userObject = jsonObject.getJSONObject("user")
                    var id = userObject.getString("_id")
                    var firstName = userObject.getString("firstName")
                    var email = userObject.getString("email")
                    var mobile = userObject.getString("mobile")
                    var password = userObject.getString("password")

                    val sharedPreference = getSharedPreferences("my_user", Context.MODE_PRIVATE)
                    val edit = sharedPreference.edit()
                    edit.putString("id", id)
                    edit.putString("token", token)
                    edit.putString("firstName", firstName)
                    edit.putString("email", email)
                    edit.putString("password", password)
                    edit.putString("mobile", mobile)
                    edit.commit()

                    var gson = Gson()
                    var LoginResponse = gson.fromJson(it.toString(), LoginResponse::class.java)
                    Toast.makeText(applicationContext, LoginResponse.token, Toast.LENGTH_LONG).show()
                    Log.d("abc", LoginResponse.token)

                    startActivity(Intent(this, HomeActivity()::class.java))

                },
                Response.ErrorListener {
                    //Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    text_view_errorLogin.text = "Cannot log in. Wrong credentials."
                    progress_bar.visibility = View.GONE
                }
            )

            requestQueue.add(request)

            //startActivity(Intent(this, HomeActivity()::class.java))
        }
    }

}