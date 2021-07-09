package com.samandroid.volleypostproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.samandroid.volleypostproject.Adapters.AdapterAddressList
import com.samandroid.volleypostproject.Models.Address
import com.samandroid.volleypostproject.Models.Urls

import kotlinx.android.synthetic.main.activity_address.*

import kotlinx.android.synthetic.main.app_bar_orange.*

class AddressActivity : AppCompatActivity(), AdapterAddressList.onAdapterListener {

    var mList: ArrayList<Address> = ArrayList()
    var addressAdapter: AdapterAddressList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)

        var toolbar = my_toolbar2
        toolbar.title = "Address"

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        init()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) { android.R.id.home -> { finish() } }
        return true
    }

    private fun init() {

        generateData()
        addressAdapter = AdapterAddressList(this, mList)
        addressAdapter?.setOnAdapterListener(this)
        recycler_view.adapter = addressAdapter
        recycler_view.layoutManager = LinearLayoutManager(this)

        button_add_new_address.setOnClickListener {
            startActivity(Intent(this, AddAddressActivity()::class.java))
        }
    }

    fun generateData() {
        var sharedPreference = getSharedPreferences("my_user", Context.MODE_PRIVATE)
        var userId: String? = null
        //userId = "60d4a9df026b6b0017d01c0c"
        userId = sharedPreference.getString("id", null)

        val url = Urls.base_url + "address/$userId"
        var requestQueue = Volley.newRequestQueue(this)
        var request = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener {
                //progress_bar.visibility = View.GONE
                var jsonArrayData = it.getJSONArray("data")
                for (i in 0 until jsonArrayData.length()) {
                    var jsonObject = jsonArrayData.getJSONObject(i)

                    var id = jsonObject.getString("_id")
                    var type = jsonObject.getString("type")
                    var houseNo = jsonObject.getInt("houseNo")
                    var streetName = jsonObject.getString("streetName")
                    var city = jsonObject.getString("city")
                    var pinCode = jsonObject.getInt("pincode")
                    //body.forEach {  }

                    var address = Address(id, type, houseNo, streetName, city, pinCode)
                    mList.add(address)
                }
                addressAdapter!!.setData(mList)
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(request)
    }

    override fun onItemClicked(address: Address) {
        var bundle = intent.getBundleExtra("PAYMENT_BUNDLE")

        bundle?.putString("CITY", address._city)
        bundle?.putString("STREET_NAME", address._streetName)
        bundle?.putString("TYPE", address._type)
        bundle?.putString("HOUSE_NO", address._houseNo.toString())
        bundle?.putString("PINCODE", address._pinCode.toString())
        var intent = Intent(this, PaymentActivity()::class.java)
        intent.putExtra("PAYMENT_BUNDLE", bundle)
        startActivity(intent)
    }

    override fun onDeleteClicked(address: Address) {
        val url = "http://apolis-grocery.herokuapp.com/api/address/${address._id}"
        var requestQueue = Volley.newRequestQueue(this)
        var request = JsonObjectRequest(
            Request.Method.DELETE,
            url,
            null,
            Response.Listener {
                //progress_bar.visibility = View.GONE
                /*var jsonArrayData = it.getJSONArray("data")
                for (i in 0 until jsonArrayData.length()) {
                    var jsonObject = jsonArrayData.getJSONObject(i)

                    var id = jsonObject.getString("_id")
                    var type = jsonObject.getString("type")
                    var houseNo = jsonObject.getInt("houseNo")
                    var streetName = jsonObject.getString("streetName")
                    var city = jsonObject.getString("city")
                    var pinCode = jsonObject.getInt("pincode")
                    //body.forEach {  }

                    var address = Address(id, type, houseNo, streetName, city, pinCode)
                    mList.add(address)
                }
                addressAdapter!!.setData(mList)*/
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(request)

        //onRestart()
        finish()
        overridePendingTransition(0, 0)
        startActivity(getIntent())
        overridePendingTransition(0, 0)
    }

    override fun onEditClicked(address: Address) {
        var bundle = Bundle()
        bundle.putString("ADDRESS_ID", address._id)
        var intent = Intent(this, EditAddressActivity()::class.java)
        intent.putExtra("ADDRESS_INFO", bundle)

        startActivity(intent)

    }

    override fun onRestart() {
        super.onRestart()
        init()
        mList = ArrayList()
    }

}