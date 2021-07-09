package com.samandroid.volleypostproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.JsonObject
import com.samandroid.volleypostproject.DBClass.DBHelper
import com.samandroid.volleypostproject.Models.Address
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.app_bar_orange.*
import org.json.JSONArray
import org.json.JSONObject

class PaymentActivity() : AppCompatActivity() {
    var pay: Double = 0.0
    var address: Address? = null

    constructor(pay: Double, address: Address): this() {
        this.pay = pay
        this.address = address
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        var toolbar = my_toolbar2
        toolbar.title = "Payment"

        setSupportActionBar(my_toolbar2)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        init()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> { finish()}
        }
        return true
    }

    private fun init() {

        var bundle = intent.getBundleExtra("PAYMENT_BUNDLE")

        text_view_pay.text = "Pay: $${bundle?.getString("PAY")}"
        text_view_type.text = "Type: ${bundle?.getString("TYPE")}"
        text_view_city.text = "City: ${bundle?.getString("CITY")}"
        text_view_house_no.text = "House number: ${bundle?.getString("HOUSE_NO")}"
        text_view_street_name.text = "Street name: ${bundle?.getString("STREET_NAME")}"
        text_view_pin_code.text = "Pin code: ${bundle?.getString("PINCODE")}"
        var sharedPreference = getSharedPreferences("my_user", Context.MODE_PRIVATE)
        val id = sharedPreference.getString("id", null)
        val email = sharedPreference.getString("email", null)
        val mobile = sharedPreference.getString("mobile", null)



        button_cash.setOnClickListener {
            var jSonObject = JSONObject()
            var jsonArray = JSONArray()

            var jSonObjectOrderSummary = JSONObject()
            jSonObjectOrderSummary.put("ourPrice", bundle?.getString("PAY"))
            jSonObjectOrderSummary.put("deliveryCharges", 0)
            jSonObjectOrderSummary.put("orderAmount", bundle?.getString("PAY"))
            jSonObject.put("orderSummary", jSonObjectOrderSummary)

            var jsonObjectPayment = JSONObject()
            jsonObjectPayment.put("paymentMode", "cash" )
            jsonObjectPayment.put("paymenStatus", "completed" )
            jSonObject.put("payment", jsonObjectPayment)

            var dbHelper = DBHelper(this)
            var mProductList = dbHelper.readProducts()

            for (i in 0..mProductList.size-1) {
                var arrayJSonObject = JSONObject()
                arrayJSonObject.put("_id", mProductList[i]._id)
                arrayJSonObject.put("mrp", mProductList[i]._mrp)
                arrayJSonObject.put("price", mProductList[i]._price)
                arrayJSonObject.put("quantity", mProductList[i]._unit)
                arrayJSonObject.put("image", mProductList[i]._image)
                jsonArray.put(arrayJSonObject)
            }
            jSonObject.put("products", jsonArray)

            var jsonObjectUser = JSONObject()
            jsonObjectUser.put("email", email)
            jsonObjectUser.put("mobile", mobile)
            jSonObject.put("user", jsonObjectUser)

            //jSonObjectOrderSummary.put("")
            jSonObject.put("userId", id)
            jSonObject.put("OrderSummary", jSonObjectOrderSummary )

            var requestQueue = Volley.newRequestQueue(this)
            var request = JsonObjectRequest(
                Request.Method.POST,
                "http://apolis-grocery.herokuapp.com/api/orders",
                jSonObject,
                Response.Listener {
                    Log.d("abc", it.toString())
                },
                Response.ErrorListener {
                    Log.e("abc", it.message.toString())
                }
            )
            requestQueue.add(request)

            startActivity(Intent(this, OrderConfirmActivity()::class.java))
        }
    }
}