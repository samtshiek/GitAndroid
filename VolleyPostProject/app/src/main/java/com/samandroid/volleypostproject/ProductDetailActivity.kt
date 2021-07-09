package com.samandroid.volleypostproject

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.samandroid.volleypostproject.DBClass.DBHelper
import com.samandroid.volleypostproject.Models.Products
import com.samandroid.volleypostproject.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.app_bar_orange.*

class ProductDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        var toolbar = my_toolbar2
        toolbar.title = "Product detail"

        setSupportActionBar(my_toolbar2)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        init()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){ android.R.id.home -> { finish() }}
        return true
    }

    private fun init() {
        var bundle = intent.getBundleExtra("PRODUCT_DETAIL")
        val productName = bundle?.getString("PRODUCT_NAME")
        text_view_product_name.text = productName
        val productMRP = bundle?.getString("PRODUCT_MRP")
        text_view_product_mrp.text = productMRP
        val productUnit = bundle?.getString("PRODUCT_UNIT")
        text_view_product_unit.text = "Unit $productUnit"
        val productPrice = bundle?.getString("PRODUCT_PRICE")
        text_view_product_price.text = "$${productPrice}"
        val productDescription = bundle?.getString("PRODUCT_DESCRIPTION")
        text_view_product_description.text = productDescription
        val productImage = bundle?.getString("PRODUCT_IMAGE")
        text_view_product_mrp.paint.flags = Paint. STRIKE_THRU_TEXT_FLAG

        val productId = bundle?.getString("PRODUCT_ID")

        Picasso.get().load("https://rjtmobile.com/grocery/images/" + productImage).centerCrop().fit().into(image_view_product_img)


        button_add_to_cart.setOnClickListener{

            var product = Products(productId.toString(), productName.toString(), productImage.toString(), productUnit.toString(), productPrice.toString().toInt(), productMRP.toString().toInt(), productDescription.toString())
            var dbHelper = DBHelper(this)
            var productList: ArrayList<Products> = dbHelper.readProducts()
            var sameProduct = false

            for (i in 0..productList.size-1) {
                if(product._id.equals(productList[i]._id)) {
                    sameProduct = true
                }
            }
            if (sameProduct != true) {
                dbHelper.addProduct(product)
                productList = dbHelper.readProducts()
            }

            startActivity(Intent(this, CartActivity(productList)::class.java))
        }
    }
}