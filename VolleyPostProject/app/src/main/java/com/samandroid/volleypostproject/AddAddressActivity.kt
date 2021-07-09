package com.samandroid.volleypostproject

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.RadioButton
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_add_address.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.app_bar_orange.*
import org.json.JSONObject

class AddAddressActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)

        var toolbar = my_toolbar2
        toolbar.title = "Add address"
        toolbar.setBackgroundColor(Color.BLACK)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        init()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) { android.R.id.home -> {finish()} }
        return true
    }

    private fun init() {
        var type = ""

        radio_group.setOnCheckedChangeListener { radio_group, checkedId -> type = findViewById<RadioButton>(checkedId).text.toString()
        text_view_radio.text = type }



        button_save.setOnClickListener {
            var sharedPreference = getSharedPreferences("my_user", Context.MODE_PRIVATE)
            var userId: String? = null
            //userId = "60d4a9df026b6b0017d01c0c"
            userId = sharedPreference.getString("id", null)

            var houseNo = edit_text_house_no.text.toString()
            var streetName = edit_text_street.text.toString()
            var city = edit_text_city.text.toString()
            var pinCode = edit_text_pincode.text.toString().toInt()

            var jsonObject = JSONObject()
            jsonObject.put("userId", userId)
            jsonObject.put("type", type)
            jsonObject.put("houseNo", houseNo)
            jsonObject.put("streetName", streetName)
            jsonObject.put("city", city)
            jsonObject.put("pincode", pinCode)

            var requestQueue = Volley.newRequestQueue(this)
            var request = JsonObjectRequest(
                Request.Method.POST,
                "http://apolis-grocery.herokuapp.com/api/address",
                jsonObject,
                Response.Listener {
                    Log.d("abc", it.toString())
                },
                Response.ErrorListener {
                    Log.e("abc", it.message.toString())
                }
            )
            requestQueue.add(request)

            finish()

        }
    }
}