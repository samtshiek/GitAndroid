package com.samandroid.volleypostproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.samandroid.volleypostproject.Adapters.AdapterCartList
import com.samandroid.volleypostproject.DBClass.DBHelper
import com.samandroid.volleypostproject.Models.Products
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.app_bar.my_toolbar
import kotlinx.android.synthetic.main.app_bar_orange.*
import kotlinx.android.synthetic.main.row_cartproduct_adapter.*
import kotlinx.android.synthetic.main.row_cartproduct_adapter.view.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

class CartActivity() : AppCompatActivity(), AdapterCartList.onAdapterListener {

    var mProductList: ArrayList<Products> = ArrayList()
    var myAdapterCartList: AdapterCartList? = null
    var totalAmount = 0.0
    var totalPay = 0.0

    constructor (productList: ArrayList<Products>) :this() {
        this.mProductList = productList
        myAdapterCartList?.setData(mProductList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        var toolbar = my_toolbar2
        toolbar.title = "Add to Cart"

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        init()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){ android.R.id.home -> { finish() }}
        return true
    }

    private fun init() {
        totalAmount = 0.0
        totalPay = 0.0

        var dbHelper = DBHelper(this)

        mProductList = dbHelper.readProducts()

        myAdapterCartList = AdapterCartList(this, mProductList)
        myAdapterCartList!!.setOnAdapterListener(this)
        //myAdapterCartList!!.setData(mProductList)
        recycler_view.adapter = myAdapterCartList
        recycler_view.layoutManager = LinearLayoutManager(this)

        for (i in 0..mProductList.size-1) {
            if(mProductList[i]._unit.toString().contains("kg")) {
                totalAmount += mProductList[i]._mrp * 1.75
                totalPay += mProductList[i]._price * 1.75
            }
            else {
                totalAmount += mProductList[i]._mrp * mProductList[i]._unit.toInt()
                totalPay += mProductList[i]._price * mProductList[i]._unit.toInt()
            }
        }

        text_view_totalAmount.text = "$${totalAmount.toString()}"
        text_view_payAmount.text = "$${totalPay.toString()}"
        totalAmount = 0.0
        totalPay = 0.0


        button_checkout.setOnClickListener{
            /*var bundle = Bundle()
            bundle.putString("PAY", totalPay.toString())
            val intent = Intent(this, AddressActivity()::class.java)
            intent.putExtra("PAY_INFO", bundle)*/

            for (i in 0..mProductList.size-1) {
                if(mProductList[i]._unit.toString().contains("kg")) {
                    totalAmount += mProductList[i]._mrp * 1.75
                    totalPay += mProductList[i]._price * 1.75
                }
                else {
                    totalAmount += mProductList[i]._mrp * mProductList[i]._unit.toInt()
                    totalPay += mProductList[i]._price * mProductList[i]._unit.toInt()
                }
            }

            var intent = Intent(this, AddressActivity()::class.java)
            var bundle = Bundle()
            bundle.putString("PAY", totalPay.toString())
            intent.putExtra("PAYMENT_BUNDLE", bundle)
            startActivity(intent)
        }
    }

   fun generateData() {
        var dbHelper = DBHelper(this)

       Timer().schedule(2000) {
           mProductList = dbHelper.readProducts()
       }

    }

    override fun onItemClicked(products: Products) {
        Toast.makeText(this, "Listens in Cart Activity!", Toast.LENGTH_LONG)
    }

    override fun onItemDeleteClicked(products: Products) {
        var dbHelper = DBHelper(this)
        dbHelper.deleteProduct(products._id)
        onRestart()
    }

    override fun onItemClicked(products: Products, view: View) {
        var dbHelper = DBHelper(this)

        mProductList = dbHelper.readProducts()

        for (i in 0..mProductList.size-1) {
            if(mProductList[i]._unit.toString().contains("kg")) {
                totalAmount += mProductList[i]._mrp * 1.75
                totalPay += mProductList[i]._price * 1.75
            }
            else {
                totalAmount += mProductList[i]._mrp * mProductList[i]._unit.toInt()
                totalPay += mProductList[i]._price * mProductList[i]._unit.toInt()
            }

        }

        text_view_totalAmount.text = totalAmount.toString()
        text_view_payAmount.text = totalPay.toString()
        totalAmount = 0.0
        totalPay = 0.0

        Toast.makeText(this, "Listens in Cart Activity!", Toast.LENGTH_LONG)
        //var total = products._mrp * products._unit.toInt()
        //text_view_totalAmount.text = total.toString()
    }

    override fun onRestart() {
        super.onRestart()
        totalAmount = 0.0
        totalPay = 0.0
        init()
    }

}