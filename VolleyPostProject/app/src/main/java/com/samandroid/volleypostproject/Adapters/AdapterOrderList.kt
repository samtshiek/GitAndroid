package com.samandroid.volleypostproject.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samandroid.volleypostproject.Models.Orders
import com.samandroid.volleypostproject.R
import kotlinx.android.synthetic.main.row_order_adapter.view.*

class AdapterOrderList(var mContext: Context, var mList: ArrayList<Orders>): RecyclerView.Adapter<AdapterOrderList.MyViewHolder>() {
    var listener: onAdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_order_adapter, parent, false)
        var myViewHolder = MyViewHolder(view)
        return myViewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var orders = mList[position]
        holder.bind(orders)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData(mList: ArrayList<Orders>) {
        this.mList = mList
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bind(order: Orders){

            itemView.text_view_date.text = order._date
            itemView.text_view_order_amount.text = "Order amount: ${order._orderAmount.toString()}"

            itemView.setOnClickListener {
                listener?.onItemClicked(order)
            }
        }
    }

    fun setOnAdapterListener(onAdapterListener: onAdapterListener){
        listener = onAdapterListener
    }

    interface onAdapterListener{
        fun onItemClicked(order: Orders)
    }
}