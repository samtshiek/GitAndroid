package com.samandroid.volleypostproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.samandroid.volleypostproject.Adapters.MyFragmentAdapter
import com.samandroid.volleypostproject.Models.Categories
import com.samandroid.volleypostproject.Models.SubCategories
import com.samandroid.volleypostproject.Models.Urls
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.activity_sub_category.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.app_bar.my_toolbar
import kotlinx.android.synthetic.main.app_bar_orange.*

class SubCategoryActivity : AppCompatActivity() {
    private var myFragmentAdapter: MyFragmentAdapter? = null
    var mCatId: Int? = null
    var myList: ArrayList<SubCategories> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)

        var toolbar = my_toolbar2
        toolbar.title = "Subcategories"

        setSupportActionBar(my_toolbar2)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        init()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){ android.R.id.home -> { finish() }}
        return true
    }

    private fun init(){
        var bundle = intent.getBundleExtra("CATEGORY_INFO")
        val catId = bundle?.getInt("catId")
        mCatId = catId
        val catName = bundle?.getString("catName")

        getSubId()

        myFragmentAdapter = MyFragmentAdapter(supportFragmentManager, catId, myList)
        view_pager.adapter = myFragmentAdapter
        tab_layout.setupWithViewPager(view_pager)


        text_view_catId.text = catId.toString()
        text_view_catName.text = catName
    }

    private fun getSubId() {
        val url = Urls.base_url + "subcategory/$mCatId"
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

                    var subId = jsonObject.getInt("subId")
                    var subName = jsonObject.getString("subName")

                    var subCategory = SubCategories(subId, subName)
                    myList.add(subCategory)
                }
                myFragmentAdapter!!.setData(myList)
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(request)
    }
}