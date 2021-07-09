package com.samandroid.volleypostproject

import android.content.Context
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
import kotlinx.android.synthetic.main.app_bar_orange.*
import org.json.JSONObject

class EditAddressActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_address)

        var toolbar = my_toolbar2
        toolbar.title = "Edit Address"

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        init()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) { android.R.id.home -> { finish() } }
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

            var bundle = intent.getBundleExtra("ADDRESS_INFO")
            val addressId = bundle?.getString("ADDRESS_ID")

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
                Request.Method.PUT,
                "http://apolis-grocery.herokuapp.com/api/address/$addressId",
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