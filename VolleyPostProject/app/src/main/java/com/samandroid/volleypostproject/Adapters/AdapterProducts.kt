package com.samandroid.volleypostproject.Adapters

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.RecyclerView
import com.samandroid.volleypostproject.Models.Categories
import com.samandroid.volleypostproject.Models.Products
import com.samandroid.volleypostproject.Models.Urls
import com.samandroid.volleypostproject.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_categories_adapter.view.*
import kotlinx.android.synthetic.main.row_subcategories_adapter.view.*

class AdapterProducts(var mContext: Context, var mList: ArrayList<Products>) : RecyclerView.Adapter<AdapterProducts.MyViewHolder>() {

    var listener: onAdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_subcategories_adapter, parent, false)
        var myViewHolder = MyViewHolder(view)
        return myViewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var products = mList[position]
        holder.bind(products)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData(mList: ArrayList<Products>) {
        this.mList = mList
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bind(products: Products){
            Picasso.get().load(Urls.base_img + products._image).centerCrop().fit().placeholder(R.drawable.ic_outline_broken_image_24).error(R.drawable.ic_outline_broken_image_24).into(itemView.image_view_product_img)
            itemView.text_view_product_name.text = products._productName
            itemView.text_view_product_price.text = "$ ${products._price.toString()}"
            itemView.text_view_product_unit.text = products._unit.toString()
            itemView.text_view_product_mrp.text = "$${products._mrp.toString()}"
            itemView.text_view_product_mrp.paint.flags = Paint. STRIKE_THRU_TEXT_FLAG

            /*Picasso.get().load("https://rjtmobile.com/grocery/images/" + products._image).into(itemView.image_view_product_img)
            itemView.text_view_product_name.text = products._productName
            itemView.text_view_product_price.text = products._price.toString()
            itemView.text_view_product_unit.text = products._unit.toString()*/


            itemView.setOnClickListener {
                //Toast.makeText(mContext, news.title, Toast.LENGTH_SHORT).show()
                listener?.onItemClicked(products)
            }
        }

    }

    fun setOnAdapterListener(onAdapterListener: onAdapterListener){
        listener = onAdapterListener
    }

    interface onAdapterListener{
        fun onItemClicked(products: Products)
    }
}