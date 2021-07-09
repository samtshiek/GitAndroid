package com.samandroid.volleypostproject.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samandroid.volleypostproject.R
import com.samandroid.volleypostproject.Models.Categories
import com.samandroid.volleypostproject.Models.Urls
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.activity_category.view.*
import kotlinx.android.synthetic.main.row_categories_adapter.view.*
import kotlinx.android.synthetic.main.row_categories_adapter.view.image_view_img

class AdapterCategories(var mContext:Context, var mList: ArrayList<Categories>): RecyclerView.Adapter<AdapterCategories.MyViewHolder>(){

    var listener: onAdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_categories_adapter, parent, false)
        var myViewHolder = MyViewHolder(view)
        return myViewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var categories = mList[position]
        holder.bind(categories)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData(mList: ArrayList<Categories>) {
        this.mList = mList
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bind(categories: Categories){

            Picasso.get().load(Urls.base_img + categories.ImageSource).into(itemView.image_view_img)
            itemView.text_view_title.text = categories._catName

            itemView.setOnClickListener {
                //Toast.makeText(mContext, news.title, Toast.LENGTH_SHORT).show()
                listener?.onItemClicked(categories)
            }
        }

    }

    fun setOnAdapterListener(onAdapterListener: onAdapterListener){
        listener = onAdapterListener
    }

    interface onAdapterListener{
        fun onItemClicked(categories: Categories)
    }

}