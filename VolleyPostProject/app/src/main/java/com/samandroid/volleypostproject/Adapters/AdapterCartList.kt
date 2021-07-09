package com.samandroid.volleypostproject.Adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.samandroid.volleypostproject.DBClass.DBHelper
import com.samandroid.volleypostproject.Models.Products
import com.samandroid.volleypostproject.Models.Urls
import com.samandroid.volleypostproject.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_cart.view.*
import kotlinx.android.synthetic.main.row_cartproduct_adapter.*
import kotlinx.android.synthetic.main.row_cartproduct_adapter.view.*
import java.util.*
import kotlin.concurrent.schedule

class AdapterCartList(var mContext: Context, var mList: ArrayList<Products>) : RecyclerView.Adapter<AdapterCartList.MyViewHolder>() {

    var listener: onAdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_cartproduct_adapter, parent, false)
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
            //Picasso.get().load("https://rjtmobile.com/grocery/images/" + products._image).placeholder(R.drawable.ic_outline_broken_image_24).error(R.drawable.ic_outline_broken_image_24).into(itemView.image_view_product_img)
            var dbHelper = DBHelper(mContext)

            Picasso.get().load(Urls.base_img + products._image).centerCrop().fit().into(itemView.image_view_product_img)
            itemView.text_view_product_name.text = products._productName
            itemView.text_view_product_mrp.text = "$ ${products._mrp.toString()}"
            itemView.text_view_product_count.text = products._unit


            Timer().schedule(3000) {

            }

            /*Picasso.get().load("https://rjtmobile.com/grocery/images/" + products._image).into(itemView.image_view_product_img)
            itemView.text_view_product_name.text = products._productName
            itemView.text_view_product_price.text = products._price.toString()
            itemView.text_view_product_unit.text = products._unit.toString()*/


            itemView.setOnClickListener {
                //Toast.makeText(mContext, news.title, Toast.LENGTH_SHORT).show()
                listener?.onItemClicked(products, it)
            }

            itemView.button_plus.setOnClickListener{

                var currentCount = itemView.text_view_product_count.text.toString().toInt()
                currentCount++
                itemView.text_view_product_count.text = currentCount.toString()

                var updatedProduct = Products(products._id, products._productName, products._image, currentCount.toString(), products._price, products._mrp, products._description)
                dbHelper.updateProduct(updatedProduct)

                listener?.onItemClicked(products, it)

            }

            itemView.button_minus.setOnClickListener{

                var currentCount = itemView.text_view_product_count.text.toString().toInt()

                if(currentCount > 0) {
                    currentCount--
                }
                itemView.text_view_product_count.text = currentCount.toString()

                var updatedProduct = Products(products._id, products._productName, products._image, currentCount.toString(), products._price, products._mrp, products._description)
                dbHelper.updateProduct(updatedProduct)

                listener?.onItemClicked(products, it)
            }

            itemView.button_delete.setOnClickListener{
                listener?.onItemDeleteClicked(products)

            }
        }

    }

    fun setOnAdapterListener(onAdapterListener: onAdapterListener){
        listener = onAdapterListener
    }

    interface onAdapterListener{
        fun onItemClicked(products: Products)
        fun onItemClicked(products: Products, view: View)
        fun onItemDeleteClicked(products: Products)
    }
}