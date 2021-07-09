package com.samandroid.volleypostproject.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.samandroid.volleypostproject.Adapters.AdapterProducts
import com.samandroid.volleypostproject.Models.Products
import com.samandroid.volleypostproject.R
import kotlinx.android.synthetic.main.fragment_1.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment2.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment2(catId: Int?, subId: Int?) : Fragment(), AdapterProducts.onAdapterListener {
    var myList: ArrayList<Products> = ArrayList()
    val mCatId = catId
    val mSubId = subId
    var adapterProducts: AdapterProducts? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_1, container, false)
        init(view)

        return view
    }

    private fun init(view: View) {
        getData()

        adapterProducts = AdapterProducts(view.context, myList)
        adapterProducts!!.setData(myList)
        adapterProducts!!.setOnAdapterListener(this)
        view.recycler_view.adapter = adapterProducts
        view.recycler_view.layoutManager = LinearLayoutManager(activity)
        //view.recycler_view.layoutManager = LinearLayoutManager(requireContext()).apply { orientation = RecyclerView.HORIZONTAL }

    }

    private fun getData(){/*

        val url = "http://apolis-grocery.herokuapp.com/api/products/cat/$mCatId"
        var requestQueue = Volley.newRequestQueue(activity)
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

                    if (subId == mSubId) {
                        var productName = jsonObject.getString("productName")
                        var unit = jsonObject.getString("unit")
                        var price = jsonObject.getInt("price")
                        var mrp = jsonObject.getInt("mrp")
                        var image = jsonObject.getString("image")

                        myList.add(Products(productName, image, unit, price.toString(), mrp))
                    }


                    //Picasso.get().load("https://rjtmobile.com/grocery/images/" + image).into(image_view_img_slider)


                    //body.forEach {  }

                }
                adapterProducts!!.setData(myList)
            },
            Response.ErrorListener {
                Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(request)

        /* myList.add(Products("ABC","iMAGE",3,20, 24))
         myList.add(Products("ABC","iMAGE",3,20, 24))
         myList.add(Products("ABC","iMAGE",3,20, 24))
         myList.add(Products("ABC","iMAGE",3,20, 24)) */
        //adapterProducts!!.setData(myList)
    */}

    override fun onItemClicked(products: Products) {
        Toast.makeText(context, "Listener listens!", Toast.LENGTH_LONG).show()
    }


}