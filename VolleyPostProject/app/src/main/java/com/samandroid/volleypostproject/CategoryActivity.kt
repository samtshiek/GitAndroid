package com.samandroid.volleypostproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.samandroid.volleypostproject.Adapters.AdapterCategories
import com.samandroid.volleypostproject.Models.Categories
import com.samandroid.volleypostproject.Models.Urls
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.app_bar.*
import org.json.JSONArray

class CategoryActivity : AppCompatActivity(), AdapterCategories.onAdapterListener {
    private var adapterCategories: AdapterCategories? = null
    var mList: ArrayList<Categories> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        var toolbar = my_toolbar
        toolbar.title = "Categories"

        setSupportActionBar(my_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){ android.R.id.home -> { finish() }}
        return true
    }

    private fun init() {
        getData()

        adapterCategories = AdapterCategories(this, mList)
        adapterCategories!!.setOnAdapterListener(this)
        recycler_view.adapter = adapterCategories
        recycler_view.layoutManager = GridLayoutManager(this, 2)
    }

    private fun getData() {
        val url = Urls.base_url + "category"
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

                    var catId = jsonObject.getInt("catId")
                    var catName = jsonObject.getString("catName")
                    var catImg = jsonObject.getString("catImage")
                    //body.forEach {  }

                    var category = Categories(catId, catName, catImg)
                    Picasso.get().load("https://rjtmobile.com/grocery/images/" + catImg).centerCrop().fit().into(image_view_img_slider)
                    mList.add(category)
                }
                adapterCategories!!.setData(mList)
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(request)
    }

    override fun onItemClicked(categories: Categories) {
        var bundle = Bundle()
        bundle.putInt("catId", categories._catId)
        bundle.putString("catName", categories._catName)
        //bundle.putString("catName", categories.ImageSource)
        var intent = Intent(this, SubCategoryActivity()::class.java)
        intent.putExtra("CATEGORY_INFO", bundle)
        startActivity(intent)
    }
}