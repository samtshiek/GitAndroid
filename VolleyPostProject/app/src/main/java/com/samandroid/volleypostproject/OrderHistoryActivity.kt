package com.samandroid.volleypostproject

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.samandroid.volleypostproject.Adapters.AdapterOrderList
import com.samandroid.volleypostproject.Models.Address
import com.samandroid.volleypostproject.Models.Orders
import com.samandroid.volleypostproject.Models.Urls
import kotlinx.android.synthetic.main.activity_order_history.*
import kotlinx.android.synthetic.main.app_bar_orange.*

class OrderHistoryActivity : AppCompatActivity() {
    var mList: ArrayList<Orders> = ArrayList()
    var adapterOrders: AdapterOrderList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)

        var toolbar = my_toolbar2
        toolbar.title = "Order history"

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        init()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){ android.R.id.home -> { finish() }}
        return true
    }

    private fun init() {
        generateData()
        adapterOrders = AdapterOrderList(this, mList)
        recycler_view.adapter = adapterOrders
        recycler_view.layoutManager = LinearLayoutManager(this)
    }

    private fun generateData() {
        var sharedPreference = getSharedPreferences("my_user", Context.MODE_PRIVATE)
        var userId: String? = null
        //userId = "60d4a9df026b6b0017d01c0c"
        userId = sharedPreference.getString("id", null)

        val url = Urls.base_url + "orders/$userId"
        var requestQueue = Volley.newRequestQueue(this)
        var request = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener {
                var jsonArrayData = it.getJSONArray("data")
                for (i in 0 until jsonArrayData.length()) {
                    var jsonObject = jsonArrayData.getJSONObject(i)

                    var date = jsonObject.getString("date")

                    var jsonObjectOrderSummary = jsonObject.getJSONObject("orderSummary")
                    var orderAmount = jsonObjectOrderSummary.getInt("orderAmount")

                    var order = Orders(orderAmount, date)
                    mList.add(order)
                }
                adapterOrders!!.setData(mList)
            },
            Response.ErrorListener {
                Log.d("abc", it.message!!)
                //Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(request)
    }
}